package eu.tutorials.authenticationwithtreblle.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.authenticationwithtreblle.ui.screens.SignUp
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel

/**Todo 2: create the MainScreen class with [NavHostController] as parameter
 * in its block we call [NavHost] and pass the variable we created as argument
 * with its startDestination set as "signup" and within the NavHost we add our first composable
 * with "signup" as its route and SignUp composable passed into it.
 * */
@Composable
fun MainScreen(navHostController: NavHostController,
               //Todo 17: we create viewModel parameter
               viewModel: MainViewModel) {
    NavHost(navController =navHostController , startDestination = "signup"){
    composable("signup"){
        SignUp(//Todo 18: we pass in its argument
            viewModel = viewModel)
    }
    }
}


