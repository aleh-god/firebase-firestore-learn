package by.godevelopment.firebasefirestorelearn.ui.screens.personslist

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
import by.godevelopment.firebasefirestorelearn.ui.interfaces.PersonsListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonsListViewModel @Inject constructor(
    private val personsListRepository: PersonsListRepository
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private val _personsListUiEvent = Channel<PersonsListUiEvent>()
    val personsListUiEvent: Flow<PersonsListUiEvent> = _personsListUiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            uiState = uiState.copy(isProcessing = true)
            val persons = personsListRepository.loadPersons()
            when (persons) {
                is FireStoreResult.Error -> {
                    Log.i("TAG#PersonsListViewModel", "${persons.message}")
                    _personsListUiEvent.send(
                        PersonsListUiEvent.ShowSnackbar(
                            UiText.StringResource(persons.message)
                        )
                    )
                }
                is FireStoreResult.Success -> {
                    uiState = uiState.copy(persons = persons.value)
                }
            }
            uiState = uiState.copy(isProcessing = false)
        }
    }

    data class UiState(
        val persons: List<Person> = emptyList(),
        val isProcessing: Boolean = false
    )
}
