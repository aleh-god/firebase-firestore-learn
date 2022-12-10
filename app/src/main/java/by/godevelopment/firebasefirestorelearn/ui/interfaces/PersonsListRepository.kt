package by.godevelopment.firebasefirestorelearn.ui.interfaces

import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person

interface PersonsListRepository {

    suspend fun loadPersons(): FireStoreResult<List<Person>>
}
