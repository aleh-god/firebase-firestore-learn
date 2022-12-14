package by.godevelopment.firebasefirestorelearn.ui.screens.updatepersons

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.UiText
import by.godevelopment.firebasefirestorelearn.ui.interfaces.UpdatePersonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePersonsViewModel @Inject constructor(
    private val updatePersonsRepository: UpdatePersonsRepository
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private val _updatePersonsUiEvent = Channel<UpdatePersonsUiEvent>()
    val updatePersonsUiEvent: Flow<UpdatePersonsUiEvent> = _updatePersonsUiEvent.receiveAsFlow()

    fun onEvent(event: UpdatePersonsUserEvent) {
        when (event) {
            UpdatePersonsUserEvent.GetNamesListAndDeletePersons -> {
                getNamesListAndDeletePersons()
            }
            UpdatePersonsUserEvent.DeletePersonsOnClick -> {
                deletePersonsByReady()
            }
            UpdatePersonsUserEvent.PersonReadyStateChanged -> {
                uiState = uiState.copy(isReady = !uiState.isReady)
            }
        }
    }

    private fun deletePersonsByReady() {
        viewModelScope.launch {
            uiState = uiState.copy(isProcessing = true)
            val result = updatePersonsRepository.deletePersons(uiState.isReady)
            uiState = uiState.copy(isProcessing = false)
            _updatePersonsUiEvent.send(
                UpdatePersonsUiEvent.ShowSnackbar(
                    UiText.StringResource(
                        resId = when (result) {
                            is FireStoreResult.Error -> {
                                Log.i("TAG#SavePersonViewModel", "Error -> ${result.message}")
                                result.message
                            }
                            is FireStoreResult.Success -> {
                                uiState = uiState.copy(personsCount = result.value)
                                R.string.message_success_save_person
                            }
                        }
                    )
                )
            )
        }
    }

    private fun getNamesListAndDeletePersons() {
        viewModelScope.launch {
            uiState = uiState.copy(isProcessing = true)
            val result = updatePersonsRepository.getNamesListAndDeletePersonsBy(uiState.isReady)
            uiState = uiState.copy(isProcessing = false)
            when (result) {
                is FireStoreResult.Error -> {
                    Log.i("TAG#SavePersonViewModel", "Error -> ${result.message}")
                    _updatePersonsUiEvent.send(
                        UpdatePersonsUiEvent.ShowSnackbar(
                            UiText.StringResource(
                                resId = result.message
                            )
                        )
                    )
                }
                is FireStoreResult.Success -> {
                    uiState = uiState.copy(value = result.value)
                }
            }
        }
    }

    data class UiState(
        val personsCount: Int = 0,
        val value: List<String> = emptyList(),
        val isProcessing: Boolean = false,
        val isReady: Boolean = false,
        val hasError: Boolean = false
    )
}