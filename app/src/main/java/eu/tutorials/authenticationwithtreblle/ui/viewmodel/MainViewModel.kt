package eu.tutorials.authenticationwithtreblle.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.authenticationwithtreblle.data.LoginUserResponse
import eu.tutorials.authenticationwithtreblle.data.RegisterUser
import eu.tutorials.authenticationwithtreblle.data.Repository
import eu.tutorials.authenticationwithtreblle.data.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository):ViewModel() {

    private val _registerRequestState = MutableStateFlow<Resource<Nothing>?>(null)
    val registerRequestState:StateFlow<Resource<Nothing>?>
    get() = _registerRequestState

    private val _userToken = MutableStateFlow<Resource<LoginUserResponse>?>(null)
    val userToken:StateFlow<Resource<LoginUserResponse>?>
        get() = _userToken

    val errorHandler = CoroutineExceptionHandler { _, error ->
        if (error is Exception) {
            _registerRequestState.value = Resource.Error(error.message!!)
            _userToken.value = Resource.Error(error.message!!)
        }
    }

    fun registerUser(registerUser: RegisterUser) {
       _registerRequestState.value = Resource.Loading(null)
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            repository.registerUser(registerUser = registerUser)
            _registerRequestState.value = Resource.Success(null)
        }
    }

    fun loginUser(username:String,password:String){
        _userToken.value = Resource.Loading(null)
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            val result = repository.loginUser(username = username, password = password)
            _userToken.value = Resource.Success(result)
        }

    }
/*Todo 11: create a saveToken and save email method, launch coroutine scope, set a delay to allow the
    token to be fetched from the server then set the save methods from the repository
 */
    //start
    fun saveToken(token:String){
        viewModelScope.launch {
            delay(500L)
            repository.saveToken(token = token)
        }
    }

    fun saveEmail(email:String){
        viewModelScope.launch {
            delay(500L)
            repository.saveEmail(email = email)
        }
    }
    //end

    /*Todo 12: We create a setter to hold each pref value, we create a method
    * to collect the value from datastore and assign to the setter and let the method return the
    * setter variable
    * */
    //start
    private val _prefToken = MutableStateFlow("")
    fun prefToken():StateFlow<String>{
        viewModelScope.launch {
            repository.tokenPref.collect {
                _prefToken.value = it
            }
        }
        return _prefToken
    }

    private val _prefEmail = MutableStateFlow<String>("")
    fun prefEmail():StateFlow<String>{
        viewModelScope.launch {
            repository.emailFlow.collect {
                _prefEmail.value = it
            }
        }
        return _prefEmail
    }
    //end
}