package by.godevelopment.firebasefirestorelearn.ui.screens.saveperson

import by.godevelopment.firebasefirestorelearn.domain.models.UiText

sealed interface SavePersonUiEvent {

    data class ShowSnackbar(val message: UiText): SavePersonUiEvent
}
