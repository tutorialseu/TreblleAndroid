package eu.tutorials.authenticationwithtreblle.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.tutorials.authenticationwithtreblle.R
import eu.tutorials.authenticationwithtreblle.data.LoginUserResponse
import eu.tutorials.authenticationwithtreblle.data.Resource
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel

//Todo 8: create MainViewmodel as a parameter
@Composable
fun Profile(viewModel: MainViewModel) {

    /*Todo 10: collect tokenState value from the viewmodel, create a context for showing
    *  a toast and a remember variable for holding the token object when it is successful*/
    //start
    val tokenState = viewModel.userToken.collectAsState().value
    val context = LocalContext.current
    val data = remember {
        mutableStateOf(LoginUserResponse())
    }
    //end

    Box(modifier = Modifier.fillMaxSize()) {
        /*TOdo 11 when tokenState is Loading we show CircularProgressIndicator
        *  with alignment set to center, if successful we set the data to the variable we  created
        *  above and when there is an error we show the error toast.
        *
        * */
        when (tokenState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success -> {
                if (tokenState.data != null) {
                    data.value = tokenState.data
                }
            }
            is Resource.Error -> {
                Toast.makeText(context, "An error has occurred", Toast.LENGTH_LONG).show()
            }

        }
            /*Todo 12: We check if the token is not empty before we set Card composable
            *  and set the email/username  to Text composable
            * */
        if (data.value.access_token.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(4), elevation = 6.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .border(
                                shape = CircleShape,
                                width = 1.dp,
                                color = Color.White
                            )
                            .clip(
                                CircleShape
                            )
                            .background(color = Color.LightGray)
                    )
                    Text(
                        text = data.value.userName, modifier = Modifier.padding(top = 10.dp)
                    )
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 32.dp)
                            .fillMaxWidth()

                    ) {
                        Text(text = "Edit Profile")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePrev() {
    //Todo 9: set default viewmodel as the parameter
    Profile(viewModel())
}

