package by.godevelopment.firebasefirestorelearn.ui.screens.saveperson

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import by.godevelopment.firebasefirestorelearn.R
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SavePersonScreen(
    scaffoldState: ScaffoldState,
    contentPadding: PaddingValues,
    viewModel: SavePersonViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.savePersonUiEvent.collect { event ->
            when (event) {
                is SavePersonUiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
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

        Text(
            text = "Persons count = ${viewModel.uiState.personsCount}",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        if (viewModel.uiState.isProcessing) LinearProgressIndicator()

        TextField(
            singleLine = true,
            maxLines = 10,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}),
            value = viewModel.uiState.name,
            onValueChange = {
                viewModel.onEvent(SavePersonUserEvent.OnNameChanged(it))
            },
            label = {
                Text(
                if (viewModel.uiState.hasError) stringResource(R.string.label_1)
                else stringResource(R.string.label_2)
            )
                    },
            isError = viewModel.uiState.hasError,
            placeholder = {
                Text(
                    text = if (viewModel.uiState.hasError) stringResource(R.string.label_2)
                    else stringResource(R.string.label_1)
                )
            },
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(checked = viewModel.uiState.isReady, onCheckedChange = {
                viewModel.onEvent(SavePersonUserEvent.PersonReadyStateChanged)
            })

            Spacer(Modifier.width(8.dp))

            Text(text = stringResource(R.string.ui_text_person_ready_state))
        }

        Button(
            onClick = { viewModel.onEvent(SavePersonUserEvent.OnSavePersonClick) },
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            )
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = stringResource(R.string.cd_icon_person),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(R.string.button_text_save))
        }
    }
}
