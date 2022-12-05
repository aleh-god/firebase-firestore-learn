package by.godevelopment.firebasefirestorelearn.di

import by.godevelopment.firebasefirestorelearn.data.sources.FireStoreSourceBehavior
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface BindModule {

    @Suppress("FunctionName")
    @Binds
    fun bindBaseImpl_to_FireStoreSource(
        baseImpl: FireStoreSourceBehavior.BaseImpl
    ): FireStoreSourceBehavior
}
