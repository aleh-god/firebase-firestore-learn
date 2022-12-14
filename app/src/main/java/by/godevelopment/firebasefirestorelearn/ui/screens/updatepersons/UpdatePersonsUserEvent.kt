package by.godevelopment.firebasefirestorelearn.ui.screens.updatepersons

sealed interface UpdatePersonsUserEvent {

    object DeletePersonsOnClick: UpdatePersonsUserEvent
    object GetNamesListAndDeletePersons: UpdatePersonsUserEvent
    object PersonReadyStateChanged : UpdatePersonsUserEvent
}
