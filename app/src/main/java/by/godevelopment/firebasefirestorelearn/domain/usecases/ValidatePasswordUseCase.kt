package by.godevelopment.firebasefirestorelearn.domain.usecases

interface ValidatePasswordUseCase {

    operator fun invoke(target: CharArray): Boolean

    class BaseImpl() : ValidatePasswordUseCase {

        override fun invoke(target: CharArray): Boolean {
            if (target.size < 8) return false
            if (target.contains(' ')) return false
            return true
        }
    }
}
