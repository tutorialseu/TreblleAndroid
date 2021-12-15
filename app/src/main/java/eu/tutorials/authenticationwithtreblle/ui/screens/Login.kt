package eu.tutorials.authenticationwithtreblle.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tutorials.authenticationwithtreblle.data.Resource
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel

//Todo 1: create a Login file and its composable with NavController and MainViewModel parameters
@Composable
fun Login(navController: NavController,viewModel: MainViewModel) {


    //Todo 7: collect the token state and assign to a variable
    val tokenState = viewModel.userToken.collectAsState().value

    /*Todo 2: create variables toggleState for showing and hiding password character,
       passwordState for password and emailState for email

     */
    //start
    val toggleState = remember {
        mutableStateOf(false)
    }

    val passwordState = remember {
        mutableStateOf("")
    }
    val emailState = remember {
        mutableStateOf("")
    }
//end

    /*Todo 3 We will have a UI similar to the SignUp  page with horizontalAlignment set to center and Button Text set to Login */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally
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
                //Todo 6: We call the loginUser from MainViewModel and set the emailState and passwordState value
                viewModel.loginUser(username = emailState.value, password = passwordState.value)
            }, modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Login", modifier = Modifier.padding(vertical = 8.dp))
        }
        /*Todo 8: we check the token state process and if its loading  we show a progress indicator
            and if it is successful we save token and email to preference datastore  and navigate into the profile screen
        */
        if (tokenState is Resource.Loading) {
            CircularProgressIndicator()
        }
        else if (tokenState is Resource.Success) {
            if (tokenState.data != null) {

                viewModel.saveToken(tokenState.data.access_token)
                viewModel.saveEmail(tokenState.data.userName)
                navController.navigate("profile"){
                    launchSingleTop = true
                    popUpTo("signup"){
                        inclusive = true
                    }
                }

            }
        }
        Divider(modifier = Modifier.padding(top = 16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp).clickable {
                    navController.navigate("signup"){
                        launchSingleTop = true
                        popUpTo("signup"){
                            inclusive = true
                        }
                    }
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Don't Have An Account? ")
            Text(text = "Sign up", fontWeight = FontWeight.Bold)
        }

    }


}