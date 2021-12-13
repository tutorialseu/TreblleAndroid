package eu.tutorials.authenticationwithtreblle.ui.screens

import android.util.Log
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

@Composable
fun Profile(viewModel: MainViewModel) {

    val tokenState = viewModel.userToken.collectAsState().value
    val context = LocalContext.current

    /*Todo 14: we remove data variable and
       collect the preference values from MainViewmodel and
    assign to a variable
     */
    //start
    val token = viewModel.prefToken().collectAsState().value
    val email = viewModel.prefEmail().collectAsState().value
   //end
    Box(modifier = Modifier.fillMaxSize()) {
        when (tokenState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success -> {
                if (tokenState.data != null) {
               /*Todo 15: if tokenstate is fetched from the network we get the toke and email
               and save it to preference data store
             */
                 //start
                    viewModel.saveToken(tokenState.data.access_token)
                    viewModel.saveEmail(tokenState.data.userName)
                    Log.d("tokenet", tokenState.data.access_token)
                    //end
                }
            }
            is Resource.Error -> {
                Toast.makeText(context, "An error has occurred", Toast.LENGTH_LONG).show()
            }

        }

        Log.d("tokenet", "$email and $token")
        /*Todo 16: Here we replace token from the internet with token from
        preference datastore. so we only fetch from the internet once where there is no
        existing user on the app.
         */
        if (token.isNotEmpty()) {
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
                        text =email, modifier = Modifier.padding(top = 10.dp)
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
    Profile(viewModel())
}

