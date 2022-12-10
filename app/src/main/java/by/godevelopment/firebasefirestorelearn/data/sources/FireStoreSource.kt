package by.godevelopment.firebasefirestorelearn.data.sources

import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FireStoreSourceBehavior {

    suspend fun savePerson(input: Person): FireStoreResult<Unit>

    suspend fun loadPersons(): FireStoreResult<List<Person>>

    suspend fun getObservablePersons(): Flow<List<Person>>

    suspend fun loadPersonsByActive(active: Boolean): FireStoreResult<List<Person>>

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

        override suspend fun loadPersons(): FireStoreResult<List<Person>> {
            return try {
                val querySnapshot = personCollectionRef.get().await()
                val persons = querySnapshot.documents.mapNotNull { it.toObject<Person>() }
                FireStoreResult.Success(value = persons)
            } catch (e: Exception) {
                FireStoreResult.Error(message = e.message.toString())
            }
        }

        override suspend fun getObservablePersons(): Flow<List<Person>> = personCollectionRef
            .snapshots(MetadataChanges.EXCLUDE)
            .map {
                it.documents.mapNotNull { doc ->
                    doc.toObject<Person>()
                }
            }

        override suspend fun loadPersonsByActive(active: Boolean): FireStoreResult<List<Person>> {
            return try {
                val querySnapshot = personCollectionRef
                    .whereEqualTo(FIELD_READY, active)
                    .orderBy(FIELD_NAME)
                    .get()
                    .await()

                val persons = querySnapshot.documents.mapNotNull { it.toObject<Person>() }

                FireStoreResult.Success(value = persons)
            } catch (e: Exception) {
                FireStoreResult.Error(message = e.message.toString())
            }
        }

        companion object {
            private const val COLLECTION_NAME = "persons"
            private const val FIELD_NAME = "name"
            private const val FIELD_READY = "ready"
        }
    }
}