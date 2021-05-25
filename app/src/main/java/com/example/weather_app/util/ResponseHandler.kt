package com.example.weather_app.util

import okhttp3.ResponseBody

sealed class ResponseHandler<out T> {

    data class Success<out T>(val data: T?) : ResponseHandler<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorMessage: String?,
        val errorBody: ResponseBody?
    ) : ResponseHandler<Nothing>()

    data class Loading<out T>(val data: T?) : ResponseHandler<T>()
}