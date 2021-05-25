package com.example.weather_app.api

import com.example.weather_app.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/forecast?")
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ): WeatherResponse
}