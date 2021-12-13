package eu.tutorials.authenticationwithtreblle.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

//Todo 2: We create a file called store_user for storing the preference values
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "store_user")
//Todo 3: create a context parameter in the class contructor
class PreferenceStore(val  context: Context){

    //Todo 4: create the key for the token and email which we will be saving to the file
    private val TOKEN_KEY = stringPreferencesKey("token_key")
    private val EMAIL_KEY = stringPreferencesKey("email_key")

    /*Todo 5: By default datastore uses coroutine and returns flow values
       so we create a variable to return the
    *  token value.
    *  First we use .catch to check if there is an IO exception while reading the value and
    *  emit an emptyPreference else we throw any other exception that might occur
    *  If there is no exception we map the value and return it
    *  */
    val tokenPref: Flow<String> = context.dataStore.data.catch { exception ->

        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[TOKEN_KEY]?:""
    }

    /*Todo 6:We do the same to return email, we create a variable and First
       we use .catch to check if there is an IO exception while reading the value and
   *  emit an emptyPreference else we throw any other exception that might occur
   *  If there is no exception we map the value and return it
   *  */
    val emailPref: Flow<String> = context.dataStore.data.catch { exception ->

        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[EMAIL_KEY]?:""
    }

    /*Todo 7: As datastore uses coroutine its edit method is a suspend function
    Here we create different functions with a string parameter to save the token and email. In each method
    we call the edit function and set the key to the token we want to save
     */
    //start
    suspend fun saveToken(token : String) {
        context.dataStore.edit { user_token ->
            user_token[TOKEN_KEY]  = token
        }
    }

    suspend fun saveEmail(email : String) {
        context.dataStore.edit { user_email ->
            user_email[EMAIL_KEY]  = email
        }
    }
//end
}