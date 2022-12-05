package by.godevelopment.firebasefirestorelearn.ui

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
import by.godevelopment.firebasefirestorelearn.ui.screens.main.MainUiEvent
import by.godevelopment.firebasefirestorelearn.ui.screens.main.MainUserEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fireStoreSourceBehavior: FireStoreSourceBehavior,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private val _mainUiEvent = Channel<MainUiEvent>()
    val mainUiEvent: Flow<MainUiEvent> = _mainUiEvent.receiveAsFlow()

    fun onEvent(event: MainUserEvent) {
        when (event) {
            is MainUserEvent.OnNameChanged -> {
                uiState = uiState.copy(
                    name = event.name,
                    hasError = false
                )
            }
            is MainUserEvent.PersonReadyStateChanged -> {
                uiState = uiState.copy(isReady = !uiState.isReady)
            }
            MainUserEvent.OnSavePersonClick -> {
                if (nameIsValid()) savePerson()
                else {
                    Log.i("TAG#MainViewModel", "else -> $uiState.name")
                    viewModelScope.launch {
                        _mainUiEvent.send(
                            MainUiEvent.ShowSnackbar(
                                UiText.DynamicString(
                                    text = "Name is not validated"
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun savePerson() {
        Log.i("TAG#MainViewModel", "savePerson")
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
                _mainUiEvent.send(
                    MainUiEvent.ShowSnackbar(
                        UiText.StringResource(
                            resId = when (result) {
                                is FireStoreResult.Error -> {
                                    Log.i("TAG#MainViewModel", "Error -> ${result.message}")
                                    R.string.message_error_save_person
                                }
                                is FireStoreResult.Success -> {
                                    Log.i("TAG#MainViewModel", "FireStoreResult.Success")
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
