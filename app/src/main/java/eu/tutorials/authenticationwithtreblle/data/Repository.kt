package eu.tutorials.authenticationwithtreblle.data

//Todo 4: Create the Repository class with AuthenticationService as its parameter
class Repository(private val authService: AuthenticationService) {

    /* Todo 5:create suspend function for registerUser with RegisterUser model as its parameter
       and in the method we call registerUser request method from the AuthenticationService and
       pass registerUser as its argument
     */
    suspend fun registerUser(registerUser: RegisterUser) {
        authService.registerUser(registerUser = registerUser)
    }
}