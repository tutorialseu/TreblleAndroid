package eu.tutorials.authenticationwithtreblle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import eu.tutorials.authenticationwithtreblle.data.Api
import eu.tutorials.authenticationwithtreblle.data.Repository
import eu.tutorials.authenticationwithtreblle.ui.MainScreen
import eu.tutorials.authenticationwithtreblle.ui.screens.SignUp
import eu.tutorials.authenticationwithtreblle.ui.theme.AuthenticationWithTreblleTheme
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    //Todo 21: we create a repository variable by lazy
    private val repository by lazy {
        Repository(Api.authService)
    }
    /*Todo 22: we initialize mainViewModel using viewModels and MainViewModelFactory that we
       have created and pass in repository
    * */
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository = repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AuthenticationWithTreblleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //Todo 23 we provide viewModel as argument
                    Authentication(viewModel = viewModel)
                }
            }
        }
    }
}



@Composable
fun Authentication(
    //Todo 19: We create a viewModel parameter
    viewModel: MainViewModel) {
    //Todo 3: we create rememberNavController variable, call MainScreen and pass in as argument
    val navController = rememberNavController()
    MainScreen(navHostController = navController,
        //Todo 20: We pass in as argument
        viewModel = viewModel)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AuthenticationWithTreblleTheme {
        Authentication(viewModel())
    }
}