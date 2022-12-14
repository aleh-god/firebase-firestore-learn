package by.godevelopment.firebasefirestorelearn.ui.screens.updatepersons

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.ui.composables.CustomButtonWithIcon

@Composable
fun UpdatePersonsScreen(
    scaffoldState: ScaffoldState,
    contentPadding: PaddingValues,
    viewModel: UpdatePersonsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.updatePersonsUiEvent.collect { event ->
            when (event) {
                is UpdatePersonsUiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(contentPadding)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (viewModel.uiState.isProcessing) LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = viewModel.uiState.isReady,
                onCheckedChange = {
                    viewModel.onEvent(UpdatePersonsUserEvent.PersonReadyStateChanged)
                }
            )

            Spacer(Modifier.width(8.dp))

            Text(text = stringResource(R.string.ui_text_person_ready_state))
        }

        CustomButtonWithIcon(
            onClick = {
                viewModel.onEvent(UpdatePersonsUserEvent.DeletePersonsOnClick)
            },
            buttonImage = Icons.Filled.Delete,
            buttonText = R.string.button_text_delete
        )

        if (viewModel.uiState.personsCount != 0) Text(
            text = stringResource(id = R.string.ui_text_del_persons_count) + viewModel.uiState.personsCount.toString(),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        CustomButtonWithIcon(
            onClick = {
                viewModel.onEvent(UpdatePersonsUserEvent.GetNamesListAndDeletePersons)
            },
            buttonImage = Icons.Filled.Edit,
            buttonText = R.string.button_text_get_delete
        )

        if (viewModel.uiState.value.isNotEmpty()) Text(
            text = StringBuilder()
                .append(stringResource(R.string.ui_text_del_persons_count))
                .append(viewModel.uiState.value.size)
                .append("\n")
                .append(viewModel.uiState.value)
                .toString(),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
}
