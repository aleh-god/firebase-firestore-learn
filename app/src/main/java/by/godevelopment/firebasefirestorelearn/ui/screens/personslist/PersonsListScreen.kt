package by.godevelopment.firebasefirestorelearn.ui.screens.personslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import by.godevelopment.firebasefirestorelearn.ui.composables.ItemsList

@Composable
fun PersonsListScreen(
    scaffoldState: ScaffoldState,
    contentPadding: PaddingValues,
    viewModel: PersonsListViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.personsListUiEvent.collect { event ->
            when (event) {
                is PersonsListUiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        ItemsList(
            isProcessing = viewModel.uiState.isProcessing,
            persons = viewModel.uiState.persons
        )
    }
}
