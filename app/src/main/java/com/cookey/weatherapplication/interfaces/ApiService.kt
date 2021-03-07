package com.cookey.weatherapplication.interfaces


import com.cookey.weatherapplication.models.WeatherCurrent
import com.cookey.weatherapplication.models.WeatherForecast
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    fun getCurrentWeather(@Query("lat") lat: String?,
                          @Query("lon") lon: String?,
                          @Query("appid") apiKey: String?): Call<WeatherCurrent>

    @GET("forecast")
    fun getWeatherForecast(@Query("lat") lat: String?,
                          @Query("lon") lon: String?,
                          @Query("appid") apiKey: String?): Call<WeatherForecast>

}