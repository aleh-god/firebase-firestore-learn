package by.godevelopment.firebasefirestorelearn.ui.screens.loadpersons

sealed interface LoadPersonsUserEvent {

    object OnLoadPersonsClick: LoadPersonsUserEvent
    object PersonsReadyStateChanged : LoadPersonsUserEvent
}
