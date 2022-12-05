package by.godevelopment.firebasefirestorelearn.domain.models

sealed interface FireStoreResult<T> {

    data class Success<T>(val value: T) : FireStoreResult<T>

    data class Error<T>(val message: String) : FireStoreResult<T>
}
