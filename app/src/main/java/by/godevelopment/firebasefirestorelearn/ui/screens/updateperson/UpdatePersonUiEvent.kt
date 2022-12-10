package by.godevelopment.firebasefirestorelearn.ui.screens.updateperson

import by.godevelopment.firebasefirestorelearn.domain.models.UiText

sealed interface UpdatePersonUiEvent {
    data class ShowSnackbar(val message: UiText): UpdatePersonUiEvent
}