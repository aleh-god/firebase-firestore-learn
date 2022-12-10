package by.godevelopment.firebasefirestorelearn.ui.interfaces

import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person

interface SavePersonRepository {

    suspend fun savePerson(input: Person): FireStoreResult<Unit>
}