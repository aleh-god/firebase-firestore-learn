package by.godevelopment.firebasefirestorelearn.ui.interfaces

import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.UpdatePersonData

interface UpdatePersonRepository {

    suspend fun updatePerson(input: UpdatePersonData): FireStoreResult<Unit>

    suspend fun deletePerson(name: String): FireStoreResult<Unit>
}