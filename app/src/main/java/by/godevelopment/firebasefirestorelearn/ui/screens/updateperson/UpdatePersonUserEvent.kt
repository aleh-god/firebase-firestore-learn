package by.godevelopment.firebasefirestorelearn.ui.screens.updateperson

sealed interface UpdatePersonUserEvent {

    object UpdatePersonOnClick: UpdatePersonUserEvent
    object DeletePersonOnClick: UpdatePersonUserEvent
    data class OldNameChanged(val oldName: String) : UpdatePersonUserEvent
    data class NewNameChanged(val newName: String) : UpdatePersonUserEvent
}
