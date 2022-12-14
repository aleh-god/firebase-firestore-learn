package by.godevelopment.firebasefirestorelearn.data.repositories

import by.godevelopment.firebasefirestorelearn.data.sources.FireStoreSourceBehavior
import by.godevelopment.firebasefirestorelearn.di.IoDispatcher
import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person
import by.godevelopment.firebasefirestorelearn.domain.models.UpdatePersonData
import by.godevelopment.firebasefirestorelearn.ui.interfaces.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val fireStoreSourceBehavior: FireStoreSourceBehavior,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SavePersonRepository,
    PersonsListRepository,
    SubscribeToPersonsRepository,
    LoadPersonsByRepository,
    UpdatePersonRepository,
    UpdatePersonsRepository
{

    override suspend fun savePerson(input: Person): FireStoreResult<Unit>
            = withContext(ioDispatcher) {
        fireStoreSourceBehavior.savePerson(input)
    }

    override suspend fun loadPersons(): FireStoreResult<List<Person>>
            = withContext(ioDispatcher) {
        fireStoreSourceBehavior.loadPersons()
    }

    override suspend fun getObservablePersons(): Flow<List<Person>> = fireStoreSourceBehavior
        .getObservablePersons()
        .flowOn(ioDispatcher)

    override suspend fun loadPersonsByReady(isReady: Boolean): FireStoreResult<List<Person>>
            = withContext(ioDispatcher) {
            fireStoreSourceBehavior.loadPersonsByReady(isReady)
    }

    override suspend fun updatePerson(input: UpdatePersonData): FireStoreResult<Unit>
            = withContext(ioDispatcher) {
        fireStoreSourceBehavior.updatePerson(input)
    }

    override suspend fun deletePerson(name: String): FireStoreResult<Unit>
            = withContext(ioDispatcher) {
        fireStoreSourceBehavior.deletePerson(name)
    }

    override suspend fun getNamesListAndDeletePersonsBy(isReady: Boolean): FireStoreResult<List<String>>
            = withContext(ioDispatcher) {
        fireStoreSourceBehavior.getNamesListAndDeletePersonsBy(isReady)
    }

    override suspend fun deletePersons(isReady: Boolean): FireStoreResult<Int>
            = withContext(ioDispatcher) {
        fireStoreSourceBehavior.deletePersons(isReady)
    }
}
