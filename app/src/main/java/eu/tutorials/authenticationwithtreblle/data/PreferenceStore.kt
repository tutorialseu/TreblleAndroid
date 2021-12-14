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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "store_user")
class PreferenceStore(val  context: Context){

    private val TOKEN_KEY = stringPreferencesKey("token_key")
    private val EMAIL_KEY = stringPreferencesKey("email_key")

    val tokenPref: Flow<String> = context.dataStore.data.catch { exception ->

        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[TOKEN_KEY]?:""
    }

    val emailPref: Flow<String> = context.dataStore.data.catch { exception ->

        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[EMAIL_KEY]?:""
    }

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

}