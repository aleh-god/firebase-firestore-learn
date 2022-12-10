package by.godevelopment.firebasefirestorelearn.domain.models

import androidx.annotation.StringRes

sealed interface FireStoreResult<T> {

    data class Success<T>(val value: T) : FireStoreResult<T>
    data class Error<T>(@StringRes val message: Int) : FireStoreResult<T>
}
