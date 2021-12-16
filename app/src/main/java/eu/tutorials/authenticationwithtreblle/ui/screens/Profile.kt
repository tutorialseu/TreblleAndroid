package eu.tutorials.authenticationwithtreblle.ui.screens

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import eu.tutorials.authenticationwithtreblle.R
import eu.tutorials.authenticationwithtreblle.Utils.base64ToByteCode
import eu.tutorials.authenticationwithtreblle.Utils.bitmapToBase64
import eu.tutorials.authenticationwithtreblle.data.LoginUserResponse
import eu.tutorials.authenticationwithtreblle.data.Resource
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel

@ExperimentalPermissionsApi
@Composable
fun Profile(viewModel: MainViewModel) {

    val context = LocalContext.current

    val token = viewModel.prefToken().collectAsState().value
    val email = viewModel.prefEmail().collectAsState().value

    val permissionsState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    var bitmap: Bitmap? = null

    imageUri?.let {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
    }
    //Todo 10: call getUserProfile from MainViewModel and pass in the email and Bearer + token as key
    viewModel.getUserProfile(username = email, key = "Bearer $token")
    Box(modifier = Modifier.fillMaxSize()) {
        /*Todo 11 we move tokenState from the been a global variable into this box to stop continous recompostion
        and only collect when token is empty
         */
        if (token.isEmpty()) {
            val tokenState = viewModel.userToken.collectAsState().value
            if (tokenState is Resource.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (tokenState is Resource.Success) {
                if (tokenState.data != null) {
                    viewModel.saveToken(tokenState.data.access_token)
                    viewModel.saveEmail(tokenState.data.userName)
                }
            }
        }
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
                    /*Todo 12: when imageResult id successful we get the base64 Image and call the method from Utils class to
                    convert back to Bitmap then set to the Image element
                     */
                    //start
                    when (val imageResult = viewModel.userImage.collectAsState().value) {
                        is Resource.Success -> {
                            if (!imageResult.data.isNullOrBlank()) {
                                imageResult.data.base64ToByteCode().let {
                                    Image(
                                        bitmap = it.asImageBitmap(),
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
                                }
                            }
                        }
                    }
                    //end

                    Text(
                        text = email, modifier = Modifier.padding(top = 10.dp)
                    )
                    Button(
                        onClick = {
                            when {
                                permissionsState.hasPermission -> {
                                    launcher.launch("image/*")
                                }

                                permissionsState.shouldShowRationale -> {
                                    Toast.makeText(
                                        context,
                                        "Read Storage permission is need to update profile ",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                else -> {
                                    permissionsState.launchPermissionRequest()
                                }
                            }
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
        bitmap?.let { btm ->
            val imageUrl = btm.bitmapToBase64()
            /*Todo 5: after we get base64 format we call the addUserImage from viewModel pass in
               the arguments and for the key we add Bearer String and the token as requested by the api
             */
            viewModel.addUserImage(username = email, imageUrl = imageUrl, key = "Bearer $token")

        }


    }

}


@ExperimentalPermissionsApi
@Preview(showBackground = true)
@Composable
fun ProfilePrev() {
    Profile(viewModel())
}

