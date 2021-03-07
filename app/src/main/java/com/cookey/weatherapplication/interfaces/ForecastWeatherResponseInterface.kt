package com.cookey.weatherapplication.interfaces

import com.cookey.weatherapplication.models.WeatherCurrent
import com.cookey.weatherapplication.models.WeatherForecast

interface ForecastWeatherResponseInterface {

    fun loadingForecastFailed(msg: String)
    fun loadingForecastSuccessful(msg: String)
    fun forecastWeatherData(data : WeatherForecast?)

}
