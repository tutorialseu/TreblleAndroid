package eu.tutorials.authenticationwithtreblle.data

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationService {

    @POST("${Api.API_PATH}Account/Register")
    suspend fun registerUser(@Body registerUser: RegisterUser)

    /**Todo 1: Create a suspend method and annotate with @Post with token as its appended path
     *annotated with [FormUrlEncoded] this is because the request has x-www-form-urlencoded
     * added as its content type meaning the required values will be added to the URL using the
     * @[Field]. For this request the api requires a grant_type which is password by default and does not
     * change so we set the value, a username which is same as the email then  a password.
     * When this request is sent we get a response in return containing the token and other useful
     * values which we have created a data class fir called [LoginUserResponse].
     *
     * Why do we call this loginUser rather than something like fetchToken? This is because it will
     * still serve as the request method to sign a user in from the login UI which we will look at later
     * */
    @POST("token")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("grant_type") type: String = "password",
        @Field("username") username: String, @Field("password") password: String
    ): LoginUserResponse
}