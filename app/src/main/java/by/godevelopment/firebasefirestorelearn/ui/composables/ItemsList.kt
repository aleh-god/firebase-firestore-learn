package by.godevelopment.firebasefirestorelearn.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.godevelopment.firebasefirestorelearn.domain.models.Person

@Composable
fun ItemsList(
    isProcessing: Boolean,
    persons: List<Person>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        if (isProcessing) LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(persons) { person ->
                PersonItem(name = person.name, isActive = person.isReady)
            }
        }
    }
}
