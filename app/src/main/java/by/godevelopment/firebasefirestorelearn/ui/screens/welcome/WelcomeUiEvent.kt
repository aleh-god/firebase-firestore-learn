package by.godevelopment.firebasefirestorelearn.ui.screens.welcome

import by.godevelopment.firebasefirestorelearn.domain.models.UiText

sealed interface WelcomeUiEvent {

    data class ShowSnackbar(val message: UiText): WelcomeUiEvent
}
