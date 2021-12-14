package eu.tutorials.authenticationwithtreblle.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import eu.tutorials.authenticationwithtreblle.ui.screens.Profile
import eu.tutorials.authenticationwithtreblle.ui.screens.SignUp
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel

@ExperimentalPermissionsApi
@Composable
fun MainScreen(navHostController: NavHostController,
               viewModel: MainViewModel) {

    NavHost(navController =navHostController , startDestination = "signup"){
    composable("signup"){
        SignUp(
            viewModel = viewModel,
        navController = navHostController
        )
    }
        composable("profile"){
            Profile(viewModel = viewModel)
        }
    }
}


