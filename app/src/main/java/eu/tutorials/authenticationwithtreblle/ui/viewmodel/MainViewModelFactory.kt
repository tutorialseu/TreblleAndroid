package eu.tutorials.authenticationwithtreblle.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.authenticationwithtreblle.data.Repository

/** Todo 11 When a subclass of [ViewModel] has a parameter which in our case is the [Repository] then we need
 * to create this factory class for it before it can be successfully initialized. So we create and
 * extend [ViewModelProvider.Factory] then override the [create] method, specify the [MainViewModel]
 * as the class we need to initialize and return it with the [repository]
 * */
class MainViewModelFactory(private val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository) as T
        }else{
            throw  ClassNotFoundException()
        }
    }
}