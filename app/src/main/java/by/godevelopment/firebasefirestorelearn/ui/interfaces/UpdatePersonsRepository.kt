package by.godevelopment.firebasefirestorelearn.ui.interfaces

import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult

interface UpdatePersonsRepository {

    suspend fun getNamesListAndDeletePersonsBy(isReady: Boolean): FireStoreResult<List<String>>

    suspend fun deletePersons(isReady: Boolean): FireStoreResult<Int>
}