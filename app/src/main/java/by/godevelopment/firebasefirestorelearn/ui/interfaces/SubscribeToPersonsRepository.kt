package by.godevelopment.firebasefirestorelearn.ui.interfaces

import by.godevelopment.firebasefirestorelearn.domain.models.Person
import kotlinx.coroutines.flow.Flow

interface SubscribeToPersonsRepository {

    suspend fun getObservablePersons(): Flow<List<Person>>
}