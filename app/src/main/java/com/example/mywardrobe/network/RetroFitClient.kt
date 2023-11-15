package com.example.mywardrobe.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient

const val APIKEY = "916a800a26e705a0532010c6effce457"

object RetrofitClient {

    val service: ApiInterface by lazy {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("access_key", APIKEY)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)

        }

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okhttp = OkHttpClient.Builder().addInterceptor(requestInterceptor).addNetworkInterceptor(interceptor).build()

        Retrofit.Builder()
            .client(okhttp)
            .baseUrl("http://api.weatherstack.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(ApiInterface::class.java)
    }

}