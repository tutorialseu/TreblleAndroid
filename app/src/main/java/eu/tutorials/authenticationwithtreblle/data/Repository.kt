package eu.tutorials.authenticationwithtreblle.data

import kotlinx.coroutines.flow.Flow

class Repository(private val authService: AuthenticationService,
val preferenceStore: PreferenceStore) {

    suspend fun registerUser(registerUser: RegisterUser) {
        authService.registerUser(registerUser = registerUser)
    }

    suspend fun loginUser(username:String,password:String):LoginUserResponse =
        authService.loginUser(username = username, password = password)

    val tokenPref: Flow<String> = preferenceStore.tokenPref

    suspend fun saveToken(token : String) {
        preferenceStore.saveToken(token = token)
    }

    val emailFlow: Flow<String> = preferenceStore.emailPref

    suspend fun saveEmail(email : String) {
        preferenceStore.saveEmail(email = email)
    }

    /* Todo 2 We create a suspend function with username, imageUrl and key parameters then call
         addUserImage from the service class and pass in as arguments
         */
    suspend fun addUserImage(username: String,imageUrl:String,key:String){
        authService.addUserImage(username = username,imageUrl = imageUrl,key = key)
    }

    /*Todo 7: We create a suspend function with parameters username and key for token
       then call getUserProfile from the service class and pass in their arguments
     */
    suspend fun getUserProfile(username:String,key:String):String =
        authService.getUserProfile(userName = username, key = key)
}