package eu.tutorials.authenticationwithtreblle.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.tutorials.authenticationwithtreblle.R

//Todo 1: create a profile file and a Profile composable within it
@Composable
fun Profile() {

    //Todo 2:We add a Box as the parent element with modifier to fillMaxSize
    Box(modifier = Modifier.fillMaxSize()) {
        /*Todo 3:We add a Card and set modifier to fillMaxWidth, alignment to center and horizontal
           padding of 32dp.
           To make the edges curved a bit we add a shape and set to RoundedCornerShape with 4 percent
           then add elevation and set to 6dp
           */
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(4), elevation = 6.dp
        ) {
            /*Todo 4: We add a Column to position the children vertically beside each other
            * with modifier set to wrapContentSize and padding top set to 16dp
            * */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 16.dp)
            ) {
                /*Todo 5 we add the Image composable for displaying profile image
                * We set a default drawable resource and set contentDescription to null,
                * we add a modifier width fixed size of 200dp, a border with CircleShape, width
                * of 1dp, and color set to white then a clip to the CircleShape with background set
                * to white.
                * */
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
//Todo 6: Below the Image we add a Text for username/email with a top padding of 10dp
                Text(
                    text = "User Name", modifier = Modifier.padding(top = 10.dp)
                )
/*Todo 7: we add a Button for editing the profile and leave onClick empty for now
*  with horizontal padding of 8dp and vertical padding of 32 dp then set to fillMaxWidth with Text
* set to "Edit Profile"
* */
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

//Todo 8: We add a Preview function to see the progress
@Preview(showBackground = true)
@Composable
fun ProfilePrev() {
    Profile()
}

