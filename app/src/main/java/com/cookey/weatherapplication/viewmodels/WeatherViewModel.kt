package com.cookey.weatherapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cookey.weatherapplication.interfaces.CurrentWeatherResponseInterface
import com.cookey.weatherapplication.interfaces.ForecastWeatherResponseInterface
import com.cookey.weatherapplication.models.WeatherCurrent
import com.cookey.weatherapplication.models.WeatherForecast
import com.cookey.weatherapplication.repository.WeatherRepository

class WeatherViewModel: ViewModel(), CurrentWeatherResponseInterface,
    ForecastWeatherResponseInterface {

    var repository: WeatherRepository = WeatherRepository()

    // Get success message from the server through the CurrentWeatherResponse Interface callback
    var successfulCurrentData = MutableLiveData<String>()

    // Get error message from the server through the CurrentWeatherResponse Interface callback
    var errorCurrentData = MutableLiveData<String>()

    // Get current weather data from the server through the CurrentWeatherResponse Interface callback
    var currentWeatherData= MutableLiveData<WeatherCurrent>()



    //Get success message from the server through the ForecastWeatherResponse Interface callback
    var successfulForecastData = MutableLiveData<String>()

    //Get error message from the server through the ForecastWeatherResponse Interface callback
    var errorForecastData = MutableLiveData<String>()

    ///Get current weather data from the server through the CurrentWeatherResponse Interface callback
    var forecastWeatherData = MutableLiveData<WeatherForecast>()


    fun postCurrentWeatherData(lat: String, long: String) {
        repository.getCurrentWeatherData(lat, long, this)
    }

    fun postForecastWeatherData(lat: String, long: String) {
        repository.getForecastWeatherData(lat, long, this)
    }




    override fun loadingCurrentFailed(msg: String) {
        errorCurrentData.postValue(msg)
    }

    override fun currentWeatherData(data: WeatherCurrent?) {
        currentWeatherData.postValue(data)
    }




    override fun loadingForecastFailed(msg: String) {
        errorForecastData.postValue(msg)
    }

    override fun loadingForecastSuccessful(msg: String) {
        successfulForecastData.postValue(msg)
    }

    override fun forecastWeatherData(data: WeatherForecast?) {
        forecastWeatherData.postValue(data)
    }


}