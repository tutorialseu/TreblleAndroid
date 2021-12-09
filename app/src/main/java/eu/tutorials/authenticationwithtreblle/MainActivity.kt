package eu.tutorials.authenticationwithtreblle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import eu.tutorials.authenticationwithtreblle.ui.theme.AuthenticationWithTreblleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthenticationWithTreblleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //Todo 2 Replace Greeting with Authentication
                 Authentication()
                }
            }
        }
    }
}

//Todo 1: Delete Greeting and create Authentication composable
@Composable
fun Authentication() {

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AuthenticationWithTreblleTheme {
        //Todo 3: Replace Greeting with Authentication
     Authentication()
    }
}