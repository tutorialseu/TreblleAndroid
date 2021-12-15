package eu.tutorials.authenticationwithtreblle.data

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationService {

    @POST("${Api.API_PATH}Account/Register")
    suspend fun registerUser(@Body registerUser: RegisterUser)

    @POST("token")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("grant_type") type: String = "password",
        @Field("username") username: String, @Field("password") password: String
    ): LoginUserResponse
}