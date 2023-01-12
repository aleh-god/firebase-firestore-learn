package by.godevelopment.firebasefirestorelearn.ui.screens.updateperson

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import by.godevelopment.firebasefirestorelearn.ui.composables.CustomButtonWithIcon

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
            text = stringResource(R.string.ui_text_old_name_value),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(Modifier.height(8.dp))

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
                viewModel.onEvent(UpdatePersonUserEvent.OldNameChanged(it))
            },
            label = {
                Text(
                    if (viewModel.uiState.oldNameHasError) stringResource(R.string.label_error_name)
                    else stringResource(R.string.label_old_name)
                )
            },
            isError = viewModel.uiState.oldNameHasError,
            placeholder = {
                Text(text = stringResource(R.string.placeholder_old_name))
            },
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.ui_text_new_name_value),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(Modifier.height(8.dp))

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
                viewModel.onEvent(UpdatePersonUserEvent.NewNameChanged(it))
            },
            label = {
                Text(
                    if (viewModel.uiState.newNameHasError) stringResource(R.string.label_error_name)
                    else stringResource(R.string.label_new_name)
                )
            },
            isError = viewModel.uiState.newNameHasError,
            placeholder = {
                Text(text = stringResource(R.string.placeholder_new_name))
            },
        )

        Spacer(Modifier.height(8.dp))

        CustomButtonWithIcon(
            onClick = {
                viewModel.onEvent(UpdatePersonUserEvent.UpdatePersonOnClick)
            },
            buttonImage = Icons.Filled.Edit,
            buttonText = R.string.button_text_update
        )

        Spacer(Modifier.height(8.dp))

        CustomButtonWithIcon(
            onClick = {
                viewModel.onEvent(UpdatePersonUserEvent.DeletePersonOnClick)
            },
            buttonImage = Icons.Filled.Delete,
            buttonText = R.string.button_text_delete
        )
    }
}
