package eu.tutorials.authenticationwithtreblle.data

import retrofit2.http.Body
import retrofit2.http.POST

//Todo 2: Create a data package and within it create an interface called Authentication
interface AuthenticationService {

    /*Todo 5: We Create a suspend method, suspend is a coroutine keyword that will enable the request be performed
       without blocking the UI thread since its a long running operation
       we annotate the method with @POST which will create a new user if successful
       In @POST annotation parameter we pass in the register path as a String and in the method parameter
       we create @Body annotation with RegisterUser object. @Body is used for sending in a form body just
       like the user name and password in the RegisterUser object
     */
    @POST("Account/Register")
    suspend fun registerUser(@Body registerUser: RegisterUser)
}