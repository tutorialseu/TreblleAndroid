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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import eu.tutorials.authenticationwithtreblle.data.Api
import eu.tutorials.authenticationwithtreblle.data.PreferenceStore
import eu.tutorials.authenticationwithtreblle.data.Repository
import eu.tutorials.authenticationwithtreblle.ui.MainScreen
import eu.tutorials.authenticationwithtreblle.ui.screens.SignUp
import eu.tutorials.authenticationwithtreblle.ui.theme.AuthenticationWithTreblleTheme
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModelFactory

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

   private val preferenceStore by lazy {
        PreferenceStore(this)
    }
    private val repository by lazy {
        Repository(Api.authService,preferenceStore = preferenceStore)
    }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository = repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AuthenticationWithTreblleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Authentication(viewModel = viewModel)
                }
            }
        }
    }
}


@ExperimentalPermissionsApi
@Composable
fun Authentication(
    viewModel: MainViewModel) {
    val navController = rememberNavController()
    MainScreen(navHostController = navController,
        viewModel = viewModel)
}

@ExperimentalPermissionsApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AuthenticationWithTreblleTheme {
        Authentication(viewModel())
    }
}