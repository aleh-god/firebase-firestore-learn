package by.godevelopment.firebasefirestorelearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.godevelopment.firebasefirestorelearn.ui.MainViewModel
import by.godevelopment.firebasefirestorelearn.ui.screens.main.MainScreen
import by.godevelopment.firebasefirestorelearn.ui.theme.FirebaseFirestoreLearnTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setContent {
            FirebaseFirestoreLearnTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "FireTopBar"
                            )
                        }
                    }
                ) { contentPadding ->
                    MainScreen(
                        scaffoldState = scaffoldState,
                        contentPadding = contentPadding,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
