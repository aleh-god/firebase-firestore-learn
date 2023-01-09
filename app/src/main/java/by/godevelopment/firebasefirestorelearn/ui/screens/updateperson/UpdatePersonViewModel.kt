package by.godevelopment.firebasefirestorelearn.ui.screens.updateperson

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.UiText
import by.godevelopment.firebasefirestorelearn.domain.models.UpdatePersonData
import by.godevelopment.firebasefirestorelearn.ui.interfaces.UpdatePersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePersonViewModel @Inject constructor(
    private val updatePersonRepository: UpdatePersonRepository
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private val _updatePersonUiEvent = Channel<UpdatePersonUiEvent>()
    val updatePersonUiEvent: Flow<UpdatePersonUiEvent> = _updatePersonUiEvent.receiveAsFlow()

    fun onEvent(event: UpdatePersonUserEvent) {
        when (event) {
            is UpdatePersonUserEvent.OldNameChanged -> {
                uiState = uiState.copy(
                    oldName = event.oldName,
                    oldNameHasError = false
                )
            }
            is UpdatePersonUserEvent.NewNameChanged -> {
                uiState = uiState.copy(
                    newName = event.newName,
                    newNameHasError = false
                )
            }
            UpdatePersonUserEvent.UpdatePersonOnClick -> {
                if (namesIsValid()) updatePerson()
                else {
                    viewModelScope.launch {
                        _updatePersonUiEvent.send(
                            UpdatePersonUiEvent.ShowSnackbar(
                                UiText.StringResource(
                                    resId = R.string.update_person_screen_error_message
                                )
                            )
                        )
                    }
                }
            }
            UpdatePersonUserEvent.DeletePersonOnClick -> {
                val oldNamesIsValid = (uiState.oldName.length in 3..10).also {
                    uiState = uiState.copy(oldNameHasError = !it)
                }
                if (oldNamesIsValid) deletePerson()
            }
        }
    }

    private fun updatePerson() {
        viewModelScope.launch {
            uiState = uiState.copy(isProcessing = true)
            val result = updatePersonRepository.updatePerson(
                UpdatePersonData(
                    newName = uiState.newName,
                    oldName = uiState.oldName
                )
            )
            uiState = uiState.copy(isProcessing = false)
            _updatePersonUiEvent.send(
                UpdatePersonUiEvent.ShowSnackbar(
                    UiText.StringResource(
                        resId = when (result) {
                            is FireStoreResult.Error -> {
                                Log.i("TAG#UpdatePersonViewModel", "Error -> ${result.message}")
                                result.message
                            }
                            is FireStoreResult.Success -> {
                                R.string.message_success_update_person
                            }
                        }
                    )
                )
            )
        }
    }

    private fun deletePerson() {
        viewModelScope.launch {
            uiState = uiState.copy(isProcessing = true)
            val result = updatePersonRepository.deletePerson(uiState.oldName)
            uiState = uiState.copy(isProcessing = false)
            _updatePersonUiEvent.send(
                UpdatePersonUiEvent.ShowSnackbar(
                    UiText.StringResource(
                        resId = when (result) {
                            is FireStoreResult.Error -> {
                                Log.i("TAG#UpdatePersonViewModel", "Error -> ${result.message}")
                                result.message
                            }
                            is FireStoreResult.Success -> { R.string.message_success_delete_person }
                        }
                    )
                )
            )
        }
    }

    private fun namesIsValid(): Boolean {
        val oldNamesIsValid = (uiState.oldName.length in 3..10).also {
            uiState = uiState.copy(oldNameHasError = !it)
        }
        val newNamesIsValid = (uiState.newName.length in 3..10).also {
            uiState = uiState.copy(newNameHasError = !it)
        }
        return oldNamesIsValid || newNamesIsValid
    }

    data class UiState(
        val isProcessing: Boolean = false,
        val oldName: String = "",
        val newName: String = "",
        val oldNameHasError: Boolean = false,
        val newNameHasError: Boolean = false
    )
}
