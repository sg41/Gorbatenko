package com.example.movies

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object RetrofitClient {

    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b" // Replace with your actual API key

    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .build()

    val kinopoiskApiService: KinopoiskApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(KinopoiskApiService::class.java)
    }

    private class ApiKeyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val originalRequest = chain.request()

            val requestBuilder = originalRequest.newBuilder()
                .header("X-API-KEY", API_KEY) // Add the X-API-KEY header with your API key
            requestBuilder.header("accept",     "application/json")

//            val originalHttpUrl: HttpUrl = originalRequest.url()
//            val url = originalHttpUrl.newBuilder()
//                .addQueryParameter("page", "1")
//                .build()
//            requestBuilder.url(url)

            val modifiedRequest = requestBuilder.build()

            return chain.proceed(modifiedRequest)
        }
    }
}
