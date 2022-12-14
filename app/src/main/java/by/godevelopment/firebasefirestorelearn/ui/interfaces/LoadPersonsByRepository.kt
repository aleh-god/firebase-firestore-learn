package by.godevelopment.firebasefirestorelearn.ui.interfaces

import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person

interface LoadPersonsByRepository {

    suspend fun loadPersonsByReady(isReady: Boolean): FireStoreResult<List<Person>>
}
