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
            //Todo 4: in the errorHandler we set the error resource to know if an error occurs
            _userImage.value = Resource.Error(error.message!!)
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

    /* Todo 3 create a method and launch a scope to process the upload and for the every
    * added image we call getUserProfile to display the image
    * */

    fun addUserImage(username: String,imageUrl:String,key:String) {
        viewModelScope.launch(Dispatchers.IO + errorHandler)  {
            repository.addUserImage(username = username,imageUrl = imageUrl,key = key)
            delay(500L)
            getUserProfile(username = username,key = key)
        }
    }
    //end

    /*Todo 8: create a setter holding the image response and getter for accessing it
    *  create a function with userName and key parameters, launch a coroutine and attach athe errorHandler to
    * the IO dispatcher. Within the launch block process the request and set the reult to Result.Success
    * */
    private val _userImage = MutableStateFlow<Resource<String>?>(null)
    val userImage:StateFlow<Resource<String>?>
        get() = _userImage

    fun getUserProfile(username:String,key:String){
        viewModelScope.launch(Dispatchers.IO+errorHandler) {
            delay(500L)
            val result  = repository.getUserProfile(username = username,key = key)
            _userImage.value = Resource.Success(result)
        }
    }
}