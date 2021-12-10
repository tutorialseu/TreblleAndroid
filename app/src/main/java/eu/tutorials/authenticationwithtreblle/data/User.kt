package eu.tutorials.authenticationwithtreblle.data


//Todo 3: create a model package and within it create a file called User where all the data classes will be created

//Todo 4: Create a data class for registering a user with email, password and confirmPassword as required by the register response
data class RegisterUser(
    val email: String,
    val password: String,
    val confirmPassword: String
)
