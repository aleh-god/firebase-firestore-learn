package by.godevelopment.firebasefirestorelearn.data.repositories

import by.godevelopment.firebasefirestorelearn.data.sources.FireStoreSourceBehavior
import by.godevelopment.firebasefirestorelearn.di.IoDispatcher
import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person
import by.godevelopment.firebasefirestorelearn.ui.interfaces.LoadPersonsByRepository
import by.godevelopment.firebasefirestorelearn.ui.interfaces.PersonsListRepository
import by.godevelopment.firebasefirestorelearn.ui.interfaces.SavePersonRepository
import by.godevelopment.firebasefirestorelearn.ui.interfaces.SubscribeToPersonsRepository
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
    LoadPersonsByRepository {

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

    override suspend fun loadPersonsByActive(isActive: Boolean): FireStoreResult<List<Person>>
            = withContext(ioDispatcher) {
            fireStoreSourceBehavior.loadPersonsByActive(isActive)
    }
}
