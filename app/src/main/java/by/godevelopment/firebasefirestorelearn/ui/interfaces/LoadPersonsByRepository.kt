package by.godevelopment.firebasefirestorelearn.ui.interfaces

import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person

interface LoadPersonsByRepository {

    suspend fun loadPersonsByActive(isActive: Boolean): FireStoreResult<List<Person>>
}
