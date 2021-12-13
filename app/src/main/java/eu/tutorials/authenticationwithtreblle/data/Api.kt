package eu.tutorials.authenticationwithtreblle.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {

    //Todo 3: remove api from the base url as token does not contain api in its path
    private val BASE_URL = "https://authteblle20211206115422.azurewebsites.net/"

    /*Todo 4:create a const variable for it as we  still need it for
       *  2 other request we will make. Now we will add this to the Register request
       * */
    const val BASE_URl_EXTENDED = "api/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val logging = HttpLoggingInterceptor()

    private val httpClient = OkHttpClient.Builder().apply {
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)

    }.build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val authService: AuthenticationService by lazy { retrofit.create(AuthenticationService::class.java) }
}