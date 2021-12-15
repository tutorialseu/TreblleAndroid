package eu.tutorials.authenticationwithtreblle.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//Todo 6: Create a singleton class for building Retrofit,Moshi and logging interceptor
object Api {
  //Todo 7: create the base url which is the consistent part of the api address
    private val BASE_URL = "https://treblledemo20211214131915.azurewebsites.net/"

    /*Todo 8: We create this path as a variable because it will be consistent and reused across 3
       of the 4 endpoints we will connecting to
     */
    const val API_PATH = "api/"

    //Todo 9: create the Moshi converter with KotlinJsonAdapterFactory for kotlin specific conversions
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    //Todo 10: create the logging interceptor to set up logging for the status when requests are been made
    private val logging = HttpLoggingInterceptor()


    //Todo 11 Add the logging interceptor to OKHttpClient
    private val httpClient = OkHttpClient.Builder().apply {
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)

    }.build()

  //Todo 12: initialise retrofit and add the moshi converter, nase url and httpClient
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    //Todo 13: create the service class using the retrofit library
    val authService: AuthenticationService by lazy { retrofit.create(AuthenticationService::class.java) }
}