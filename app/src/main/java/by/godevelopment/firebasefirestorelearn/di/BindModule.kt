package by.godevelopment.firebasefirestorelearn.di

import by.godevelopment.firebasefirestorelearn.data.repositories.RepositoryImplementation
import by.godevelopment.firebasefirestorelearn.data.sources.FireStoreSourceBehavior
import by.godevelopment.firebasefirestorelearn.ui.interfaces.LoadPersonsByRepository
import by.godevelopment.firebasefirestorelearn.ui.interfaces.PersonsListRepository
import by.godevelopment.firebasefirestorelearn.ui.interfaces.SavePersonRepository
import by.godevelopment.firebasefirestorelearn.ui.interfaces.SubscribeToPersonsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("FunctionName")
@InstallIn(SingletonComponent::class)
@Module
interface BindModule {

    @Binds
    fun bindBaseImpl_to_FireStoreSource(
        baseImpl: FireStoreSourceBehavior.BaseImpl
    ): FireStoreSourceBehavior

    @Binds
    fun repository_to_SavePersonRepository(
        repositoryImplementation: RepositoryImplementation
    ): SavePersonRepository

    @Binds
    fun repository_to_PersonsListRepository(
        repositoryImplementation: RepositoryImplementation
    ): PersonsListRepository

    @Binds
    fun repository_to_SubscribeToPersonsRepository(
        repositoryImplementation: RepositoryImplementation
    ): SubscribeToPersonsRepository

    @Binds
    fun repository_to_LoadPersonsByRepository(
        repositoryImplementation: RepositoryImplementation
    ): LoadPersonsByRepository
}
