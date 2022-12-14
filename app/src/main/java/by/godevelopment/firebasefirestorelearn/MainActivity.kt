package by.godevelopment.firebasefirestorelearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.godevelopment.firebasefirestorelearn.navigation.Route
import by.godevelopment.firebasefirestorelearn.ui.screens.loadpersons.LoadPersonScreen
import by.godevelopment.firebasefirestorelearn.ui.screens.personslist.PersonsListScreen
import by.godevelopment.firebasefirestorelearn.ui.screens.saveperson.SavePersonScreen
import by.godevelopment.firebasefirestorelearn.ui.screens.updateperson.UpdatePersonScreen
import by.godevelopment.firebasefirestorelearn.ui.screens.updatepersons.UpdatePersonsScreen
import by.godevelopment.firebasefirestorelearn.ui.screens.welcome.WelcomeScreen
import by.godevelopment.firebasefirestorelearn.ui.theme.FirebaseFirestoreLearnTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setContent {
            FirebaseFirestoreLearnTheme {

                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    topBar = {}
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.WELCOME.label
                    ) {
                        composable(Route.WELCOME.label) {
                            WelcomeScreen(
                                onClickItem = {
                                    navController.navigate(it) {
                                        popUpTo(Route.WELCOME.label)
                                    }
                                },
                                contentPadding = padding)
                        }
                        composable(Route.SAVE_PERSON.label) {
                            SavePersonScreen(scaffoldState = scaffoldState, contentPadding = padding)
                        }
                        composable(Route.LOAD_PERSON.label) {
                            LoadPersonScreen(scaffoldState = scaffoldState, contentPadding = padding)
                        }
                        composable(Route.UPDATE_PERSON.label) {
                            UpdatePersonScreen(scaffoldState = scaffoldState, contentPadding = padding)
                        }
                        composable(Route.LIST_PERSONS.label) {
                            PersonsListScreen(scaffoldState = scaffoldState, contentPadding = padding)
                        }
                        composable(Route.UPDATE_PERSONS.label) {
                            UpdatePersonsScreen(scaffoldState = scaffoldState, contentPadding = padding)
                        }
                    }
                }
            }
        }
    }
}
