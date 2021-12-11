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
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: Repository):ViewModel() {

    private val _registerRequestState = MutableStateFlow<Resource<Nothing>?>(null)
    val registerRequestState:StateFlow<Resource<Nothing>?>
    get() = _registerRequestState

    /*Todo 5: create a setter to hold the value from the login request
       and a getter to expose this value to composables that needs it*/
    private val _userToken = MutableStateFlow<Resource<LoginUserResponse>?>(null)
    val userToken:StateFlow<Resource<LoginUserResponse>?>
        get() = _userToken

    val errorHandler = CoroutineExceptionHandler { _, error ->
        if (error is Exception) {
            _registerRequestState.value = Resource.Error(error.message!!)
            //Todo 6: set error message to ResourceError if an error occurs
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

    /*Todo 7: create a function to process user token request, set Resource.Loading before launching the
    *  request and Resource.Success when the result is successful*/
    fun loginUser(username:String,password:String){
        _userToken.value = Resource.Loading(null)
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            delay(500L)
            val result = repository.loginUser(username = username, password = password)
            _userToken.value = Resource.Success(result)
        }

    }

}