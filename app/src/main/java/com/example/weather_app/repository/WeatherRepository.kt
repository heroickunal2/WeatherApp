package com.example.weather_app.repository

import com.example.weather_app.api.WeatherService

class WeatherRepository(private val weatherService: WeatherService):BaseRepository() {

    suspend fun getWeatherByCity(city: String) = safeApiCall{
        weatherService.getWeatherByCity(city)
    }
}