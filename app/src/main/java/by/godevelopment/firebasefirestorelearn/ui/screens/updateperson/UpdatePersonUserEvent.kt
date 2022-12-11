package by.godevelopment.firebasefirestorelearn.ui.screens.updateperson

sealed interface UpdatePersonUserEvent {
    object UpdatePersonOnClick: UpdatePersonUserEvent
    object DeletePersonOnClick: UpdatePersonUserEvent
    data class OldNameOnChanged(val oldName: String) : UpdatePersonUserEvent
    data class NewNameOnChanged(val newName: String) : UpdatePersonUserEvent
}