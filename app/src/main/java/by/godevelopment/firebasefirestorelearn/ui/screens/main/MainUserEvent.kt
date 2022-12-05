package by.godevelopment.firebasefirestorelearn.ui.screens.main

sealed interface MainUserEvent {
    object OnSavePersonClick: MainUserEvent
    object PersonReadyStateChanged : MainUserEvent
    data class OnNameChanged(val name: String) : MainUserEvent
}
