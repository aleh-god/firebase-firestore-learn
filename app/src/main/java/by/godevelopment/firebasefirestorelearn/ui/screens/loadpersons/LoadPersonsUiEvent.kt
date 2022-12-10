package by.godevelopment.firebasefirestorelearn.ui.screens.loadpersons

import by.godevelopment.firebasefirestorelearn.domain.models.UiText

sealed interface LoadPersonsUiEvent {

    data class ShowSnackbar(val message: UiText): LoadPersonsUiEvent
}
