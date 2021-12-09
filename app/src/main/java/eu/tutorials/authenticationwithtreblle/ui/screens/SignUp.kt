package eu.tutorials.authenticationwithtreblle.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//Todo 4: create screens package for the app screens, create SignUp file with a SignUp composable
@Composable
fun SignUp() {
    //Todo 9: Lets create a remember boolean variable called toggleState for managing the password field visibility
    val toggleState = remember {
        mutableStateOf(false)
    }
//Todo 13: Create a remember String variable called passwordState for tracking entered password characters
    val passwordState = remember {
        mutableStateOf("")
    }

    //Todo 16: Create a remember String variable called emailState for tracking entered email characters
    val emailState = remember {
        mutableStateOf("")
    }
    /*Todo 5: Add a Column as the parent layout and set a modifier to fillMaxSize with horizontal
    padding of 16dp. then set verticalArrangement to center

     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        /*Todo 6: In the Column we add OutlinedTextField composable
           which is an input field for the email address. This element must have a value and
           onValueChange property for keeping track of the entered characters. we will leave them
           empty for now. We also add a modifier and set it to fillMaxWidth, a label set to show
           Text with Email Address to let the user know the expected value and keyboardOptions with
           keyboardType set to Email to make sure that email specific characters are shown on the
           keyboard
         */
        OutlinedTextField(
            //Todo 17: update the value to emailState value and set emailState value to the result from onValueChange
            value = emailState.value, onValueChange = {
               emailState.value = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = "Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        /*Todo 8: Below the Email field we add another field for password with a trailingIcon and set
        its content to IconButton that when clicked will hide or show the password characters

          */
        OutlinedTextField(
            //Todo 14: assign passwordState value to value and set its value to the result from onValueChanged to get the characters been typed into the field
            value =passwordState.value, onValueChange = {
            passwordState.value = it
            },
            modifier = Modifier
                .fillMaxWidth().padding(vertical = 16.dp),
            label = { Text(text = "Email Password") },
            trailingIcon = {
                IconButton(onClick = {
                    //Todo 11 We change toggleState when the button is clicked
                    toggleState.value = !toggleState.value
                } ) {
                    /*Todo 12: We then add an Icon  and imageVector is set to Visibility if toggleState
                    is true and VisibilityOff if toggleState is false
                   */
                    Icon(
                        imageVector = if (toggleState.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = ""
                    )
                }
            },
            /*Todo 15: We add visualTransformation and set to None when toggleState is true and when
             false set to PasswordVisualTransformation()
             */
            visualTransformation = if (toggleState.value) VisualTransformation.None else
                PasswordVisualTransformation()
        )
/*Todo 18 we add a button and set Modifier to fillMaxWidth with content set to Text with text as Sign
*  up and padding vertical set to 8dp */
        Button(
            onClick = {},  modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Sign Up", modifier = Modifier.padding(vertical = 8.dp))
        }
        //Todo 19 We add a Divider with padding top set to 16dp
        Divider(modifier = Modifier.padding(top = 16.dp))
        //Todo 20 we add a Row for existing user with text to click and navigate to the Login screen
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
}


//Todo 7: Create a preview function for SignUp
@Preview(showBackground = true)
@Composable
fun SignupPrev() {
    SignUp()
}