package by.godevelopment.firebasefirestorelearn.ui.screens.saveperson

sealed interface SavePersonUserEvent {

    object SavePersonOnClick: SavePersonUserEvent
    object PersonReadyStateChanged : SavePersonUserEvent
    data class NameChanged(val name: String) : SavePersonUserEvent
}
