package eu.tutorials.authenticationwithtreblle.data

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST("Account/Register")
    suspend fun registerUser(@Body registerUser: RegisterUser)
}