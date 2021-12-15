package eu.tutorials.authenticationwithtreblle.data

import retrofit2.http.*

interface AuthenticationService {

    @POST("${Api.BASE_URl_EXTENDED}Account/Register")
    suspend fun registerUser(@Body registerUser: RegisterUser)

    @POST("token")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("grant_type") type: String = "password",
        @Field("username") username: String, @Field("password") password: String
    ): LoginUserResponse

    @FormUrlEncoded
    @POST("${Api.BASE_URl_EXTENDED}UserProfile")
    suspend fun addUserImage(
        @Field("UserName") username: String,
        @Field("ProfileImage") imageUrl: String,
        @Header("Authorization") key: String
    )

    @GET("${Api.BASE_URl_EXTENDED}UserProfile")
    suspend fun getUserProfile(
        @Query("UserName") userName: String,
        @Header("Authorization") key: String
    ):String
}