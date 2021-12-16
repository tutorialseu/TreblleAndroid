package eu.tutorials.authenticationwithtreblle

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

object Utils {

    fun Bitmap.bitmapToBase64():String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }


    /*Todo 12 we create a String extension function to directly convert the base64 String returned
      back to a Bitmap
     */
    fun String.base64ToByteCode():Bitmap {
        val decodedString =  Base64.decode(this.substring(this.indexOf(",") + 1), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }
}