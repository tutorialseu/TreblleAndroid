package eu.tutorials.authenticationwithtreblle.data

data class RegisterUser(
    val email: String,
    val password: String,
    val password_Confirmation: String
)

data class LoginUserResponse(
    val access_token: String="",
    val token_type: String="",
    val expires_in: String="",
    val userName:String=""
)