package eu.tutorials.authenticationwithtreblle.data

data class RegisterUser(
    val email: String,
    val password: String,
    val confirmPassword: String
)
