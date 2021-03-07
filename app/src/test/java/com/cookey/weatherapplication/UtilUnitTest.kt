package com.cookey.weatherapplication



import com.cookey.weatherapplication.models.WeatherCurrent
import com.cookey.weatherapplication.models.WeatherForecast
import com.cookey.weatherapplication.utils.formatTodayOfTheWeek
import org.junit.Assert
import org.junit.Test
import com.cookey.weatherapplication.utils.isConnectedToTheInternet
import com.cookey.weatherapplication.utils.updateImage
import com.cookey.weatherapplication.utils.updateWeatherIcon
import org.junit.Before


class UtilUnitTest {


    @Test
    fun `Test the WeatherForecast model is not null when instantiated` () {
        val weatherForecast = WeatherForecast()
        Assert.assertNotNull(weatherForecast)
        Assert.assertNull(weatherForecast.city)
    }


    @Test
    fun `Test the WeatherCurrent model is not null when instantiated` () {
        val weatherCurrent = WeatherCurrent()
        Assert.assertNotNull(weatherCurrent)
        Assert.assertNull(weatherCurrent.weather)
    }


    @Test
    fun `Test that the date formatted works properly` () {

        Assert.assertEquals( "Monday 07:00", formatTodayOfTheWeek("2021-03-08 07:00:00"))

    }


    @Test
    fun `test Weather Icon is accurate`() {
        Assert.assertEquals( R.mipmap.clear, updateWeatherIcon(800))
        Assert.assertEquals( R.mipmap.partly_sunny, updateWeatherIcon(780))
        Assert.assertEquals( R.mipmap.rain, updateWeatherIcon(510))
    }

    @Test
    fun `test Weather image is accurate` () {

        Assert.assertEquals( R.drawable.sea_cloudy, updateImage(710))
        Assert.assertEquals( R.drawable.sea_rainy, updateImage(100))
        Assert.assertEquals( R.drawable.sea_sunnypng, updateImage(904))
    }




}