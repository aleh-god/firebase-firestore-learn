package by.godevelopment.firebasefirestorelearn.ui.screens.welcome

sealed interface WelcomeUserEvent {
    data class OnClickItem(val itemLabel: String): WelcomeUserEvent
}
