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

}