package eu.tutorials.authenticationwithtreblle.ui.screens


import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.tutorials.authenticationwithtreblle.data.RegisterUser
import eu.tutorials.authenticationwithtreblle.data.Resource
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel

@Composable
fun SignUp(viewModel: MainViewModel) {

    val registerState = viewModel.registerRequestState.collectAsState().value
    //Todo 9: We collect the user token state
    val tokenState = viewModel.userToken.collectAsState().value
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
                    confirmPassword = passwordState.value)
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

   when(registerState){
       is Resource.Loading->{
           Toast.makeText(context,"registration in Progress",Toast.LENGTH_LONG).show()
       }

       is Resource.Success->{
           Toast.makeText(context,"User SuccessFully Registered",Toast.LENGTH_LONG).show()
       }

       is Resource.Error->{
           Toast.makeText(context,"An error has occurred",Toast.LENGTH_LONG).show()
           //TOdo 8: When a user successfully registers we can request for a token with the username and password
           viewModel.loginUser(username = emailState.value,password = passwordState.value)
       }
   }
        /*Todo 10 we monitor the token state and when its loading we can show a toast message like
            onboarding user, when its successful we need to save this token somewhere because it does not expire until
            a certain period of time which we will see when we log the response and then take the user
            into the app and in this case is the profile screen so it can be used for accessing or performing
            other user specific operations like uploading profile image and fetching it back to display on the screen.
        *   and if there is an error we show a toast with the error message. For now we will just log the token response
        * */
        when(tokenState){
            is Resource.Loading ->{
                Toast.makeText(context,"onboarding user",Toast.LENGTH_LONG).show()
            }

            is Resource.Success->{
                Log.d("Token","${tokenState.data}")
            }

            is Resource.Error ->{
                Toast.makeText(context,"An error has occurred",Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPrev() {
    SignUp(viewModel())
}