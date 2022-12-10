package by.godevelopment.firebasefirestorelearn.ui.screens.loadpersons

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
import by.godevelopment.firebasefirestorelearn.ui.interfaces.LoadPersonsByRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadPersonsViewModel @Inject constructor(
    private val loadPersonsByRepository: LoadPersonsByRepository
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private val _loadPersonsUiEvent = Channel<LoadPersonsUiEvent>()
    val loadPersonsUiEvent: Flow<LoadPersonsUiEvent> = _loadPersonsUiEvent.receiveAsFlow()

    fun onEvent(event: LoadPersonsUserEvent) {
        when (event) {
            LoadPersonsUserEvent.OnLoadPersonsClick -> loadPersonsByState()
            LoadPersonsUserEvent.PersonsReadyStateChanged -> {
                uiState = uiState.copy(
                    personsReadyState = !uiState.personsReadyState
                )
            }
        }
    }

    private fun loadPersonsByState() {
        viewModelScope.launch {
            uiState = uiState.copy(isProcessing = true)
            val result = loadPersonsByRepository.loadPersonsByActive(uiState.personsReadyState)
            when (result) {
                is FireStoreResult.Error -> {
                    Log.i("TAG#", "loadPersonsByState: ${result.message}")
                    uiState = uiState.copy(isProcessing = false)
                    _loadPersonsUiEvent.send(
                        LoadPersonsUiEvent.ShowSnackbar(
                            UiText.StringResource(result.message)
                        )
                    )
                }
                is FireStoreResult.Success -> {
                    uiState = uiState.copy(
                        isProcessing = false,
                        persons = result.value
                    )
                }
            }
        }
    }

    data class UiState(
        val isProcessing: Boolean = false,
        val personsReadyState: Boolean = false,
        val persons: List<Person> = emptyList(),
    )
}
