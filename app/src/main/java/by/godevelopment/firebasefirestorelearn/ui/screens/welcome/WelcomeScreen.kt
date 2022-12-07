package by.godevelopment.firebasefirestorelearn.ui.screens.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.godevelopment.firebasefirestorelearn.navigation.Route

@Composable
fun WelcomeScreen(
    contentPadding: PaddingValues,
    onClickItem: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Route.values()
            .forEach {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    onClick = {
                        onClickItem(it.label)
                    }
                ) {
                    Text(
                        text = stringResource(id = it.screenName),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
    }
}
