package by.godevelopment.firebasefirestorelearn.ui.screens.updatepersons

import by.godevelopment.firebasefirestorelearn.domain.models.UiText

sealed interface UpdatePersonsUiEvent {

    data class ShowSnackbar(val message: UiText): UpdatePersonsUiEvent
}
