package eu.tutorials.authenticationwithtreblle.data

/**Todo 6:Create Resource class to manage request or response process
 * If [Loading] , if there is an [Error] or when it there is a [Success]
 * */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}