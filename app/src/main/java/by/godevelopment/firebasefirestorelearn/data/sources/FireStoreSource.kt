package by.godevelopment.firebasefirestorelearn.data.sources

import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.domain.models.FireStoreResult
import by.godevelopment.firebasefirestorelearn.domain.models.Person
import by.godevelopment.firebasefirestorelearn.domain.models.UpdatePersonData
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.SetOptions
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

    suspend fun loadPersonsByReady(isReady: Boolean): FireStoreResult<List<Person>>

    suspend fun updatePerson(input: UpdatePersonData): FireStoreResult<Unit>

    suspend fun deletePerson(name: String): FireStoreResult<Unit>

    suspend fun getNamesListAndDeletePersonsBy(isReady: Boolean): FireStoreResult<List<String>>

    suspend fun deletePersons(isReady: Boolean): FireStoreResult<Int>

    class BaseImpl @Inject constructor() : FireStoreSourceBehavior {

        private val personCollectionRef = Firebase.firestore.collection(COLLECTION_NAME)

        private suspend fun getQuerySnapshotByString(key: String) = personCollectionRef
            .whereEqualTo(FIELD_NAME, key)
            .get()
            .await()

        private suspend fun getQuerySnapshotByReady(key: Boolean) = personCollectionRef
            .whereEqualTo(FIELD_READY, key)
            .orderBy(FIELD_NAME)
            .get()
            .await()

        override suspend fun savePerson(input: Person): FireStoreResult<Unit> {
            return try {
                personCollectionRef.add(input).await()
                FireStoreResult.Success(Unit)
            } catch (e: Exception) {
                FireStoreResult.Error(message = R.string.message_error_save_person)
            }
        }

        override suspend fun loadPersons(): FireStoreResult<List<Person>> {
            return try {
                val querySnapshot = personCollectionRef.get().await()
                val persons = querySnapshot.documents.mapNotNull { it.toObject<Person>() }
                FireStoreResult.Success(value = persons)
            } catch (e: Exception) {
                FireStoreResult.Error(message = R.string.message_error_load_data)
            }
        }

        override suspend fun getObservablePersons(): Flow<List<Person>> = personCollectionRef
            .snapshots(MetadataChanges.EXCLUDE)
            .map {
                it.documents.mapNotNull { doc ->
                    doc.toObject<Person>()
                }
            }

        override suspend fun loadPersonsByReady(isReady: Boolean): FireStoreResult<List<Person>> {
            return try {
                val querySnapshot = getQuerySnapshotByReady(isReady)
                val persons = querySnapshot.documents.mapNotNull { it.toObject<Person>() }
                FireStoreResult.Success(value = persons)
            } catch (e: Exception) {
                FireStoreResult.Error(message = R.string.message_error_load_data)
            }
        }

        override suspend fun updatePerson(input: UpdatePersonData): FireStoreResult<Unit> {
            val map = mapOf<String, Any>(
                FIELD_NAME to input.newName
            )
            return try {
                val oldPersonQuery = getQuerySnapshotByString(input.oldName)
                if (oldPersonQuery.documents.isNotEmpty()) {
                    for (document in oldPersonQuery) {
                        personCollectionRef
                            .document(document.id)
                            .set(
                                map,
                                SetOptions.merge()
                            )
                            .await()
                    }
                } else {
                    return FireStoreResult.Error(message = R.string.message_error_matched_person)
                }
                FireStoreResult.Success(Unit)
            } catch (e: Exception) {
                FireStoreResult.Error(message = R.string.message_error_updated_data)
            }
        }

        override suspend fun deletePerson(name: String): FireStoreResult<Unit> {
            return try {
                val oldPersonQuery = getQuerySnapshotByString(name)
                if (oldPersonQuery.documents.isNotEmpty()) {
                    for (document in oldPersonQuery) {
                        personCollectionRef
                            .document(document.id)
                            .delete()
                            .await()
                    }
                } else {
                    return FireStoreResult.Error(message = R.string.message_error_matched_person)
                }
                FireStoreResult.Success(Unit)
            } catch (e: Exception) {
                FireStoreResult.Error(message = R.string.message_error_deleted_data)
            }
        }

        override suspend fun getNamesListAndDeletePersonsBy(isReady: Boolean): FireStoreResult<List<String>> {
            return try {
                val references = getQuerySnapshotByReady(isReady)
                    .documents
                    .map { it.reference }
                val namesList = mutableListOf<String?>()

                Firebase.firestore.runTransaction { transaction ->
                    references
                        .onEach { reference ->
                            namesList.add(transaction.get(reference).getString(FIELD_NAME))
                        }
                        .onEach { reference ->
                            transaction.delete(reference)
                        }
                    null
                }.await()
                namesList.filterNotNull().let {
                    if (it.isNotEmpty()) FireStoreResult.Success(value = it)
                    else FireStoreResult.Error(message = R.string.message_error_deleted_data)
                }
            } catch (e: Exception) {
                FireStoreResult.Error(message = R.string.message_error_deleted_data)
            }
        }

        override suspend fun deletePersons(isReady: Boolean): FireStoreResult<Int> {
            var count = 0
            return try {
                val documents = getQuerySnapshotByReady(isReady)
                    .documents
                    .map { it.reference }
                Firebase.firestore.runBatch { batch ->
                    documents.onEach {
                        batch.delete(it)
                    }
                }.await()
                FireStoreResult.Success(value = count)
            } catch (e: Exception) {
                FireStoreResult.Error(message = R.string.message_error_deleted_data)
            }
        }

        companion object {
            private const val COLLECTION_NAME = "persons"
            private const val FIELD_NAME = "name"
            private const val FIELD_READY = "ready"
        }
    }
}