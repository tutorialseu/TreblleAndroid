package eu.tutorials.authenticationwithtreblle.ui.screens


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.authenticationwithtreblle.data.RegisterUser
import eu.tutorials.authenticationwithtreblle.data.Resource
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel

@Composable
fun SignUp(viewModel: MainViewModel,
           navController: NavController) {
    val registerState = viewModel.registerRequestState.collectAsState().value
    val context = LocalContext.current

    val toggleState = remember {
        mutableStateOf(false)
    }

    val passwordState = remember {
        mutableStateOf("")
    }
    val emailState = remember {
        mutableStateOf("")
    }

    val tokenPref = viewModel.prefToken().collectAsState().value
    if (tokenPref.isNotEmpty()){
        navController.navigate("profile"){
            launchSingleTop = true
            popUpTo("login"){
                inclusive = true
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = emailState.value, onValueChange = {
                emailState.value = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = "Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = passwordState.value, onValueChange = {
                passwordState.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            label = { Text(text = "Email Password") },
            trailingIcon = {
                IconButton(onClick = {
                    toggleState.value = !toggleState.value
                }) {

                    Icon(
                        imageVector = if (toggleState.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = ""
                    )
                }
            },

            visualTransformation = if (toggleState.value) VisualTransformation.None else
                PasswordVisualTransformation()
        )
        Button(
            onClick = {
                val registerUser = RegisterUser(email = emailState.value,password = passwordState.value,
                    password_confirmation = passwordState.value)
                viewModel.registerUser(registerUser = registerUser)
            }, modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Sign Up", modifier = Modifier.padding(vertical = 8.dp))
        }

        Divider(modifier = Modifier.padding(top = 16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already Have An Account? ")
            Text(text = "Login", fontWeight = FontWeight.Bold)
        }

    }

    when(registerState){
        is Resource.Loading->{
            Toast.makeText(context,"registration in Progress",Toast.LENGTH_LONG).show()
        }

        is Resource.Success->{
                viewModel.loginUser(username = emailState.value, password = passwordState.value)
            navController.navigate("profile"){
                launchSingleTop = true
                popUpTo("signup"){
                    inclusive = true
                }
            }
        }

        is Resource.Error->{
            Toast.makeText(context,"An error has occurred",Toast.LENGTH_LONG).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPrev() {
    SignUp(viewModel(),
        rememberNavController())
}