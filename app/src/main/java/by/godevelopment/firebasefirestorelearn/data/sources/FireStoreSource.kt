package by.godevelopment.firebasefirestorelearn.data.sources

import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FireStoreSourceBehavior {

    suspend fun savePerson(input: Person): FireStoreResult<Unit>

    class BaseImpl @Inject constructor() : FireStoreSourceBehavior {

        private val personCollectionRef = Firebase.firestore.collection(COLLECTION_NAME)

        override suspend fun savePerson(input: Person): FireStoreResult<Unit> {
            return try {
                personCollectionRef.add(input).await()
                FireStoreResult.Success(Unit)
            } catch (e: Exception) {
                FireStoreResult.Error(
                    message = e.message.toString()
                )
            }
        }

        companion object {
            private val COLLECTION_NAME = "persons"
        }
    }
}