package by.godevelopment.firebasefirestorelearn.ui.screens.personslist

import by.godevelopment.firebasefirestorelearn.domain.models.UiText

sealed interface PersonsListUiEvent {

    data class ShowSnackbar(val message: UiText): PersonsListUiEvent
}
