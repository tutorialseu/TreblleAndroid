package eu.tutorials.authenticationwithtreblle

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

object Utils {

    /* Todo 14: we create an extension function as Bitmap so we can easily
        called the method from the bitmap version
        of the URI and we are returning a String. In this function we first compress
        the image to reduce its size after its selected, convert it to  byteArray and then to base64.
        */
    fun Bitmap.bitmapToBase64():String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }


}