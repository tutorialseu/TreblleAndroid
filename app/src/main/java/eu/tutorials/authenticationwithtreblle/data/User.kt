package eu.tutorials.authenticationwithtreblle.data

data class RegisterUser(
    val email: String,
    val password: String,
    val password_confirmation: String
)

//Todo 2: We create a response format to suit the response to be returned by the token request
data class LoginUserResponse(
    val access_token: String="",
    val token_type: String="",
    val expires_in: String="",
    val userName:String=""
)