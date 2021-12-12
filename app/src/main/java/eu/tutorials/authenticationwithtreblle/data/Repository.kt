package eu.tutorials.authenticationwithtreblle.data

class Repository(private val authService: AuthenticationService) {

    suspend fun registerUser(registerUser: RegisterUser) {
        authService.registerUser(registerUser = registerUser)
    }
}