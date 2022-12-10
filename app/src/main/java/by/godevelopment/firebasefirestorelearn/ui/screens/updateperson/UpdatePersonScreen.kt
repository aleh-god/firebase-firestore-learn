package by.godevelopment.firebasefirestorelearn.ui.screens.updateperson

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UpdatePersonScreen(
    scaffoldState: ScaffoldState,
    contentPadding: PaddingValues,
    viewModel: UpdatePersonViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.updatePersonUiEvent.collect { event ->
            when (event) {
                is UpdatePersonUiEvent.ShowSnackbar -> {
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
            text = "Old name value:",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(Modifier.height(4.dp))

        TextField(
            singleLine = true,
            maxLines = 10,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            value = viewModel.uiState.oldName,
            onValueChange = {
                viewModel.onEvent(UpdatePersonUserEvent.OldNameOnChanged(it))
            },
            label = {
                Text(
                    if (viewModel.uiState.oldNameHasError) stringResource(R.string.label_1)
                    else stringResource(R.string.label_2)
                )
            },
            isError = viewModel.uiState.oldNameHasError,
            placeholder = {
                Text(
                    text = if (viewModel.uiState.oldNameHasError) stringResource(R.string.label_2)
                    else stringResource(R.string.label_1)
                )
            },
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "New name value:",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(Modifier.height(4.dp))

        TextField(
            singleLine = true,
            maxLines = 10,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            value = viewModel.uiState.newName,
            onValueChange = {
                viewModel.onEvent(UpdatePersonUserEvent.NewNameOnChanged(it))
            },
            label = {
                Text(
                    if (viewModel.uiState.newNameHasError) stringResource(R.string.label_1)
                    else stringResource(R.string.label_2)
                )
            },
            isError = viewModel.uiState.newNameHasError,
            placeholder = {
                Text(
                    text = if (viewModel.uiState.newNameHasError) stringResource(R.string.label_2)
                    else stringResource(R.string.label_1)
                )
            },
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onEvent(UpdatePersonUserEvent.OnUpdatePersonClick) },
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
            Text(stringResource(R.string.button_text_update))
        }
    }
}
