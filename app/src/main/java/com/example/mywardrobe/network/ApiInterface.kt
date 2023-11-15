package com.example.mywardrobe.network

import com.example.mywardrobe.data.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    // The api's Data  take someTime soo that's why we will be using Defferend
    @GET("current")
    suspend fun getCurrentWeatherAsync(@Query("query") mLocation: String ): WeatherResponse

}