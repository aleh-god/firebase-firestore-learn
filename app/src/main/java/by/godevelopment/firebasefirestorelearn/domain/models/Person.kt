package by.godevelopment.firebasefirestorelearn.domain.models

import com.google.firebase.firestore.PropertyName

data class Person(
    val name: String = "",
    @PropertyName("ready")
    val isReady: Boolean = false
)
