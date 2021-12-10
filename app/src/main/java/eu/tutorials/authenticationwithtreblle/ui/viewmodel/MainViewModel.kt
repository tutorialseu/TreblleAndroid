package eu.tutorials.authenticationwithtreblle.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.authenticationwithtreblle.data.RegisterUser
import eu.tutorials.authenticationwithtreblle.data.Repository
import eu.tutorials.authenticationwithtreblle.data.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

/**Todo 7: We create viewmodel package and then MainViewModel class with [Repository] as its parameter which its data depends on
*  And extend the [ViewModel] */
class MainViewModel(private val repository: Repository):ViewModel() {

    /**Todo 8:We will create a setter variable [_registerRequestState] for tracking the registerUser request
    *  which will return a MutableStateFlow with the Resource type of Nothing, we use Nothing when
    *  we are not expecting any data. This will only be mutable in the MainViewModel class
    *  so we have made it private. Then we create a [_registerRequestState] as the getter which can
     *  be accessible outside of this class*/
    private val _registerRequestState = MutableStateFlow<Resource<Nothing>?>(null)
    val registerRequestState:StateFlow<Resource<Nothing>?>
    get() = _registerRequestState

    /**Todo 9: We also create a [CoroutineExceptionHandler] since we are using the coroutine APIs
     * it will help us track errors that might occur during request process.
     * The error handler returns [Exception] so using the [_registerRequest] variable
     * which is a type of the [Resource] class we created we retrieve any error message from the Exception
     * Exception
     * */
    val errorHandler = CoroutineExceptionHandler { _, error ->
        if (error is Exception) {
            _registerRequestState.value = Resource.Error(error.message!!)
        }
    }

    /**Todo 10:We now create a function to process the register request
     * This function has [RegisterUser] as parameter, before we launch the [viewModelScope] for
     * the suspend function we set [Resource.Loading], within the launch parameter we specify an
     * IO dispatcher for the request so it is processed outside of the UI thread then attach the errorHandler we
     * created and in the lunch block we call registerUser from the repository to process the request and if the request is successful
     * we can set [Resource.Success] with null because we are not expecting any data in return
     * */
    fun registerUser(registerUser: RegisterUser) {
       _registerRequestState.value = Resource.Loading(null)
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            repository.registerUser(registerUser = registerUser)
            _registerRequestState.value = Resource.Success(null)
        }
    }

}