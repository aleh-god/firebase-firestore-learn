package by.godevelopment.firebasefirestorelearn.ui.screens.saveperson

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person
import by.godevelopment.firebasefirestorelearn.domain.models.UiText
import by.godevelopment.firebasefirestorelearn.ui.interfaces.SavePersonRepository
import by.godevelopment.firebasefirestorelearn.ui.interfaces.SubscribeToPersonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavePersonViewModel @Inject constructor(
    private val savePersonRepository: SavePersonRepository,
    private val subscribeToPersonsRepository: SubscribeToPersonsRepository
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private val _savePersonUiEvent = Channel<SavePersonUiEvent>()
    val savePersonUiEvent: Flow<SavePersonUiEvent> = _savePersonUiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        toSubscribeToPersons()
    }

    private fun toSubscribeToPersons() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            subscribeToPersonsRepository
                .getObservablePersons()
                .onStart { uiState = uiState.copy(isProcessing = true) }
                .catch {
                    uiState = uiState.copy(isProcessing = false)
                    _savePersonUiEvent.send(
                        SavePersonUiEvent.ShowSnackbar(
                            UiText.StringResource(
                                resId = R.string.message_error_load_data
                            )
                        )
                    )
                }
                .collect {
                    uiState = uiState.copy(
                        isProcessing = false,
                        personsCount = it.size
                    )
                }
        }
    }

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
        viewModelScope.launch {
                uiState = uiState.copy(isProcessing = true)
                val result = savePersonRepository.savePerson(
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
                                    R.string.message_success_save_person
                                }
                            }
                        )
                    )
                )
        }
    }

    private fun nameIsValid(): Boolean {
        return (uiState.name.length in 3..10).also {
            uiState = uiState.copy(hasError = !it)
        }
    }

    data class UiState(
        val personsCount: Int = 0,
        val name: String = "",
        val isReady: Boolean = false,
        val isProcessing: Boolean = false,
        val hasError: Boolean = false
    )
}
