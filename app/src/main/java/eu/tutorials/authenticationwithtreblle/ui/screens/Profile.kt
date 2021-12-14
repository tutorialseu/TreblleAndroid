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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import eu.tutorials.authenticationwithtreblle.R
import eu.tutorials.authenticationwithtreblle.Utils.bitmapToBase64
import eu.tutorials.authenticationwithtreblle.data.LoginUserResponse
import eu.tutorials.authenticationwithtreblle.data.Resource
import eu.tutorials.authenticationwithtreblle.ui.viewmodel.MainViewModel

//Todo 4: Because permission api is experimental we add the annotation
@ExperimentalPermissionsApi
@Composable
fun Profile(viewModel: MainViewModel) {

    val tokenState = viewModel.userToken.collectAsState().value
    val context = LocalContext.current

    val token = viewModel.prefToken().collectAsState().value
    val email = viewModel.prefEmail().collectAsState().value

    //Todo 3: We create a variable and assign READ_EXTERNAL_STORAGE permission
    val permissionsState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )

    //Todo 5: create a variable to hold the uri to the image from gallery
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    //Todo 6: We create a launcher to open the gallery expecting a result which will be the image uri
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    //Todo 9: Log the image to confirm it was collected successfully
    Log.d("imageuri","$imageUri")

    /*Todo 15 create a bitmap variable and convert the image Uri to bitmap*/
    //start
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
    //end


    Box(modifier = Modifier.fillMaxSize()) {
        when (tokenState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success -> {
                if (tokenState.data != null) {

                    viewModel.saveToken(tokenState.data.access_token)
                    viewModel.saveEmail(tokenState.data.userName)

                }
            }
            is Resource.Error -> {
                Toast.makeText(context, "An error has occurred", Toast.LENGTH_LONG).show()
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
                    //Todo 16 using the method from Utils class convert the bitmap  to base64
                    //start
                    bitmap?.let { btm ->
                        val imageUrl = btm.bitmapToBase64()
                        Log.d("base64",imageUrl)
                    }
                    //end
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
                            /*Todo 8: when edit profile button is clicked we check if permission to
                            *  access external storage has been accepted then launch the gallery
                            *  with available images.
                            * If permission should showRationale then show a message to let the user know that the permission
                            * needs to be accepted.
                            * If not launch request for permission */
                            when {
                                permissionsState.hasPermission -> {
                                    launcher.launch("image/*")
                                }
                                /*
                                shouldShowRationale is setup to not keep forcing the user to accept permission after 2 denies
                                but you only tell them why permission is needed then they can go to settings and enable it themselves
                                 */
                                permissionsState.shouldShowRationale -> {
                                    Toast.makeText(context,"Read Storage permission is need to update profile ",Toast.LENGTH_LONG).show()
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
    }
}

//Todo 7: Add the experimentalPermissions annotation
@ExperimentalPermissionsApi
@Preview(showBackground = true)
@Composable
fun ProfilePrev() {
    Profile(viewModel())
}

