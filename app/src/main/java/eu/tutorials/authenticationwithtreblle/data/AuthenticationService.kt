package eu.tutorials.authenticationwithtreblle.data

import eu.tutorials.authenticationwithtreblle.data.model.RegisterUser
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST("Account/Register")
    suspend fun registerUser(@Body registerUser: RegisterUser)
}