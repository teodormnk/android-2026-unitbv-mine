package cst.unitbvfmi2026.network

import cst.unitbvfmi2026.BuildConfig
import cst.unitbvfmi2026.network.apis.UserApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL =
        "https://reqres.in" // gandit sa fie intr-un singur loc, toate request-urile se vor folosi de el

    private const val ARG_REQRES_KEY = "x-api-key" // key-ul folosit in header

    private val loggingInterceptor =
        HttpLoggingInterceptor().apply { // ne ajuta la logarea tuturor request-urilor
            level = HttpLoggingInterceptor.Level.BODY //logheaza informatiile basic si include body
        }

    private val reqresKeyInterceptor = Interceptor { chain -> //"blocheaza" putin requestul ce s-a executat, injecteaza acest header si il lasa sa mearga mai departe
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header(ARG_REQRES_KEY, BuildConfig.reqres_key)
            .build()
        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(reqresKeyInterceptor)
        .build()

    val usersApi: UserApiService by lazy { //aici se construieste body-ul
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }
}