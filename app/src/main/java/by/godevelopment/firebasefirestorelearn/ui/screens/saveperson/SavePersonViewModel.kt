package by.godevelopment.firebasefirestorelearn.ui.screens.saveperson

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.data.sources.FireStoreSourceBehavior
import by.godevelopment.firebasefirestorelearn.di.IoDispatcher
import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person
import by.godevelopment.firebasefirestorelearn.domain.models.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SavePersonViewModel @Inject constructor(
    private val fireStoreSourceBehavior: FireStoreSourceBehavior,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private val _savePersonUiEvent = Channel<SavePersonUiEvent>()
    val savePersonUiEvent: Flow<SavePersonUiEvent> = _savePersonUiEvent.receiveAsFlow()

    fun onEvent(event: SavePersonUserEvent) {
        when (event) {
            is SavePersonUserEvent.OnNameChanged -> {
                uiState = uiState.copy(
                    name = event.name,
                    hasError = false
                )
            }
            is SavePersonUserEvent.PersonReadyStateChanged -> {
                uiState = uiState.copy(isReady = !uiState.isReady)
            }
            SavePersonUserEvent.OnSavePersonClick -> {
                if (nameIsValid()) savePerson()
                else {
                    Log.i("TAG#SavePersonViewModel", "else -> $uiState.name")
                    viewModelScope.launch {
                        _savePersonUiEvent.send(
                            SavePersonUiEvent.ShowSnackbar(
                                UiText.StringResource(
                                    resId = R.string.save_person_screen_error_message
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun savePerson() {
        Log.i("TAG#SavePersonViewModel", "savePerson")
        viewModelScope.launch {
            withContext(ioDispatcher) {
                uiState = uiState.copy(isProcessing = true)
                val result = fireStoreSourceBehavior.savePerson(
                    Person(
                        name = uiState.name,
                        isReady = uiState.isReady
                    )
                )
                uiState = uiState.copy(isProcessing = false)
                _savePersonUiEvent.send(
                    SavePersonUiEvent.ShowSnackbar(
                        UiText.StringResource(
                            resId = when (result) {
                                is FireStoreResult.Error -> {
                                    Log.i("TAG#SavePersonViewModel", "Error -> ${result.message}")
                                    R.string.message_error_save_person
                                }
                                is FireStoreResult.Success -> {
                                    Log.i("TAG#SavePersonViewModel", "FireStoreResult.Success")
                                    R.string.message_success_save_person
                                }
                            }
                        )
                    )
                )
            }
        }
    }

    private fun nameIsValid(): Boolean {
        return (uiState.name.length in 3..10).also {
            uiState = uiState.copy(hasError = !it)
        }
    }

    data class UiState(
        val name: String = "",
        val isReady: Boolean = false,
        val isProcessing: Boolean = false,
        val hasError: Boolean = false
    )
}
