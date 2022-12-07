package by.godevelopment.firebasefirestorelearn.ui.screens.saveperson

sealed interface SavePersonUserEvent {
    object OnSavePersonClick: SavePersonUserEvent
    object PersonReadyStateChanged : SavePersonUserEvent
    data class OnNameChanged(val name: String) : SavePersonUserEvent
}
