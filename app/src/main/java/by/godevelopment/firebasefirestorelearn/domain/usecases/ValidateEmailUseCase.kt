package by.godevelopment.firebasefirestorelearn.domain.usecases

import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import javax.inject.Inject

interface ValidateEmailUseCase {

    operator fun invoke(target: String): Boolean

    class BaseImpl @Inject constructor() : ValidateEmailUseCase {

        override fun invoke(target: String): Boolean {
            return if (target.isNotEmpty()) {
                EMAIL_ADDRESS.matcher(target.trim()).matches()
            } else false
        }
    }
}
