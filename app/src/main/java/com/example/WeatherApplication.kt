package com.example

import android.app.Application
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.di.networkModule
import com.example.weatherapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Enable logging for only debug hence we don't use the default Logger class anywhere
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@WeatherApplication)
            // declare modules
            modules(listOf(networkModule, viewModelModule))
        }
    }
}