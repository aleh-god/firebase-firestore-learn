package by.godevelopment.firebasefirestorelearn.ui.screens.loadpersons

sealed interface LoadPersonsUserEvent {

    object LoadPersonsOnClick: LoadPersonsUserEvent
    object PersonsReadyStateChanged : LoadPersonsUserEvent
}
