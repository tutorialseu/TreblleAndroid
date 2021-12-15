package eu.tutorials.authenticationwithtreblle.data

import retrofit2.http.*

interface AuthenticationService {

    @POST("${Api.API_PATH}Account/Register")
    suspend fun registerUser(@Body registerUser: RegisterUser)

    @POST("token")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("grant_type") type: String = "password",
        @Field("username") username: String, @Field("password") password: String
    ): LoginUserResponse

    /* Todo 1 We create a function and add the @FormUrlEncoded annotation, a @Post with the rest of
         its path "api/UserProfile" attached, for the function parameter we use  @Field on the
         variables with the value expected for username and image respectively.  We add a new
         annotation @Header with a value of "Authorization" which we use to set the token.
        Without the correct token passed in belonging to that user we cannot make changes to their account.
        */
    @FormUrlEncoded
    @POST("${Api.API_PATH}UserProfile")
    suspend fun addUserImage(
        @Field("UserName") username: String,
        @Field("ProfileImage") imageUrl: String,
        @Header("Authorization") key: String
    )

    /* Todo 6: Create a function with @Query parameter and UserName value,
    *   Add @Header annotation with Authorization value for setting the token*/
    @GET("${Api.API_PATH}UserProfile")
    suspend fun getUserProfile(
        @Query("UserName") userName: String,
        @Header("Authorization") key: String
    ):String
}