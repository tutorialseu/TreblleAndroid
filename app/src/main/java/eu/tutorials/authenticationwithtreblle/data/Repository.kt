package eu.tutorials.authenticationwithtreblle.data

import kotlinx.coroutines.flow.Flow

class Repository(private val authService: AuthenticationService,
                 //Todo 8:create reference for PreferenceStore
val preferenceStore: PreferenceStore) {

    suspend fun registerUser(registerUser: RegisterUser) {
        authService.registerUser(registerUser = registerUser)
    }

    suspend fun loginUser(username:String,password:String):LoginUserResponse =
        authService.loginUser(username = username, password = password)

   //Todo 9: create variable to return tokenPref and function saveToken from PreferenceStore class
    //start
    val tokenPref: Flow<String> = preferenceStore.tokenPref

    suspend fun saveToken(token : String) {
        preferenceStore.saveToken(token = token)
    }
//end


    //Todo 10: create variable to return emailPref and function saveEmail from PreferenceStore class
    //start
    val emailFlow: Flow<String> = preferenceStore.emailPref

    suspend fun saveEmail(email : String) {
        preferenceStore.saveEmail(email = email)
    }
    //end
}