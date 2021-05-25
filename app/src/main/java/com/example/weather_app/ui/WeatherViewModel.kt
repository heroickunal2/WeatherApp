package com.example.weather_app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.model.WeatherResponse
import com.example.weather_app.repository.WeatherRepository
import com.example.weather_app.util.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherViewModel(private val weatherRepo: WeatherRepository): ViewModel(){

    private val _GetWeatherByCityResponse = MutableLiveData<ResponseHandler<WeatherResponse>>()
    val getWeatherByCityResponse: LiveData<ResponseHandler<WeatherResponse>> = _GetWeatherByCityResponse

    fun getWeatherByCity(city:String) {
        Timber.d("city $city")
        _GetWeatherByCityResponse.postValue(ResponseHandler.Loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            _GetWeatherByCityResponse.postValue(weatherRepo.getWeatherByCity(city))
        }
    }

}