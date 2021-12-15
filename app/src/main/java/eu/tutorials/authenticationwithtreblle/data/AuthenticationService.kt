package eu.tutorials.authenticationwithtreblle.data

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    //Todo 25: add the api path
    @POST("${Api.API_PATH}Account/Register")
    suspend fun registerUser(@Body registerUser: RegisterUser)
}