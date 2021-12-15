package eu.tutorials.authenticationwithtreblle.data

class Repository(private val authService: AuthenticationService) {

    suspend fun registerUser(registerUser: RegisterUser) {
        authService.registerUser(registerUser = registerUser)
    }

    /*
    Todo 3: We create a suspend function with username and password parameters to return the loginUser
     request from the service class */
    suspend fun loginUser(username:String,password:String):LoginUserResponse =
        authService.loginUser(username = username, password = password)
}