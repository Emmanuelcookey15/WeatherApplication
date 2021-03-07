package com.cookey.weatherapplication.interfaces

import com.cookey.weatherapplication.models.WeatherCurrent

interface CurrentWeatherResponseInterface {

    fun loadingCurrentFailed(msg: String)
    fun loadingCurrentSuccessful(msg: String)
    fun currentWeatherData(data : WeatherCurrent?)

}
