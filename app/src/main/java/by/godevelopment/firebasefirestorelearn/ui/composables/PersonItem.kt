package by.godevelopment.firebasefirestorelearn.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.godevelopment.firebasefirestorelearn.R

@Composable
fun PersonItem(
    name: String,
    isActive: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = name)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = if (isActive) Icons.Filled.Person else Icons.Filled.Close,
            contentDescription = stringResource(R.string.cd_icon_person),
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
    }
}


