package by.godevelopment.firebasefirestorelearn.ui.screens.main

import by.godevelopment.firebasefirestorelearn.domain.models.UiText

sealed interface MainUiEvent {
//    object Success: MainUiEvent
    data class ShowSnackbar(val message: UiText): MainUiEvent
}
