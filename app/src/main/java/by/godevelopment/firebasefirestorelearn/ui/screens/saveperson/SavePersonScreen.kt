package by.godevelopment.firebasefirestorelearn.ui.screens.saveperson

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
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
import androidx.hilt.navigation.compose.hiltViewModel
import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.ui.composables.CustomButtonWithIcon

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

        Text(
            text = stringResource(R.string.ui_header_text_pers_count)
                    + viewModel.uiState.personsCount,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        TextField(
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            value = viewModel.uiState.name,
            onValueChange = {
                viewModel.onEvent(SavePersonUserEvent.NameChanged(it))
            },
            label = {
                Text(
                    if (viewModel.uiState.hasError) stringResource(R.string.label_error_name)
                    else stringResource(R.string.label_name)
                )
            },
            isError = viewModel.uiState.hasError,
            placeholder = {
                Text(text = stringResource(R.string.placeholder_name))
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

        CustomButtonWithIcon(
            onClick = {
                viewModel.onEvent(SavePersonUserEvent.SavePersonOnClick)
            },
            buttonImage = Icons.Filled.Send,
            buttonText = R.string.button_text_save
        )
    }
}
