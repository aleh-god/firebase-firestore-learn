package by.godevelopment.firebasefirestorelearn.ui.screens.loadpersons

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.godevelopment.firebasefirestorelearn.R
import by.godevelopment.firebasefirestorelearn.ui.composables.CustomButtonWithIcon
import by.godevelopment.firebasefirestorelearn.ui.composables.ItemsList

@Composable
fun LoadPersonScreen(
    scaffoldState: ScaffoldState,
    contentPadding: PaddingValues,
    viewModel: LoadPersonsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.loadPersonsUiEvent.collect { event ->
            when (event) {
                is LoadPersonsUiEvent.ShowSnackbar -> {
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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Choose players status",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Checkbox(
            checked = viewModel.uiState.personsReadyState,
            onCheckedChange = { viewModel.onEvent(LoadPersonsUserEvent.PersonsReadyStateChanged) }
        )

        CustomButtonWithIcon(
            onClick = {
                viewModel.onEvent(LoadPersonsUserEvent.LoadPersonsOnClick)
            },
            buttonImage =  Icons.Filled.Person,
            buttonText = R.string.button_text_load
        )

        Spacer(Modifier.height(8.dp))

        ItemsList(
            isProcessing = viewModel.uiState.isProcessing,
            persons = viewModel.uiState.persons
        )
    }
}
