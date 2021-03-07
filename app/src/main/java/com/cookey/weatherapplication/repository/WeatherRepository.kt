package com.cookey.weatherapplication.repository

import android.util.Log
import com.cookey.weatherapplication.api.RetrofitClient
import com.cookey.weatherapplication.interfaces.CurrentWeatherResponseInterface
import com.cookey.weatherapplication.interfaces.ForecastWeatherResponseInterface
import com.cookey.weatherapplication.models.WeatherCurrent
import com.cookey.weatherapplication.models.WeatherForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    val API_KEY = "d79bb69dbdda3f95c0516479746c0fff"

    fun getCurrentWeatherData(lat: String, long: String, callback: CurrentWeatherResponseInterface) {

        RetrofitClient.getInstance()?.getApi()?.getCurrentWeather(lat, long, API_KEY)?.enqueue(object :
            Callback<WeatherCurrent> {
            override fun onFailure(call: Call<WeatherCurrent>, t: Throwable) {
                callback.loadingCurrentFailed(t.toString())
            }

            override fun onResponse(call: Call<WeatherCurrent>, response: Response<WeatherCurrent>) {

                if (response.isSuccessful && response.body() != null) {
                    when (response.code()) {
                        200 -> {
                            response.body()?.let {
                                callback.currentWeatherData(it)
                            }

                            callback.loadingCurrentSuccessful("Weather data is valid")
                        }

                        else -> {
                            callback.loadingCurrentFailed("This request is Invalid.. please try again")
                        }
                    }
                } else {
                    callback.loadingCurrentFailed("Invalid request..Check your card details and try again")
                }
            }
        })
    }



    fun getForecastWeatherData(lat: String, long: String, callback: ForecastWeatherResponseInterface) {

        RetrofitClient.getInstance()?.getApi()?.getWeatherForecast(lat, long, API_KEY)?.enqueue(object :
            Callback<WeatherForecast> {
            override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                callback.loadingForecastFailed(t.toString())
                Log.d("TELLUS", t.toString())
            }

            override fun onResponse(call: Call<WeatherForecast>, response: Response<WeatherForecast>) {

                if (response.isSuccessful && response.body() != null) {
                    when (response.code()) {
                        200 -> {
                            response.body()?.let {
                                callback.forecastWeatherData(it)
                            }

                            callback.loadingForecastSuccessful("Forecasted Weather data Loaded succesfully")
                            Log.d("TELLUS", response.message())
                        }

                        else -> {
                            callback.loadingForecastFailed("This request is Invalid.. please try again")
                            Log.d("TELLUS", response.message())
                        }
                    }
                } else {
                    callback.loadingForecastFailed("Invalid request..Check your card details and try again")
                    Log.d("TELLUS", response.message())
                }
            }
        })
    }



}