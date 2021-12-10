package eu.tutorials.authenticationwithtreblle.ui.screens


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

//Todo 12: create MainViewModel as a parameter
@Composable
fun SignUp(viewModel: MainViewModel) {
    /*Todo 15:To track the request progress we create a variable and collect the state set to registerUser
    *  Below the state we create a context variable to be used with the Toast
    * */
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
                /*Todo 14 In Signup button onClick we create RegisterUser and pass in the value from
                   the emailState to email passwordState as password and confirmPassword. Then we call
                   registerUser from viewModel and pass in registerUser
                * */
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

        /*Todo 16:  Here we check is th State is loading we make a simple toast to show its loading,
            if its successful we show a Success message and if there is an error we show an error message
            and check the Logcat to see what the error could be because we have added a logging interceptor
            to our library to communicate what message is coming from the server or network
         */

   when(registerState){
       is Resource.Loading->{
           Toast.makeText(context,"registration in Progress",Toast.LENGTH_LONG).show()
       }

       is Resource.Success->{
           Toast.makeText(context,"User SuccessFully Registered",Toast.LENGTH_LONG).show()
       }

       is Resource.Error->{
           Toast.makeText(context,"An error has occurred",Toast.LENGTH_LONG).show()
       }
   }
    }
}

//Todo 13:provide a default argument for MainViewModel
@Preview(showBackground = true)
@Composable
fun SignupPrev() {
    SignUp(viewModel())
}