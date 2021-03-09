package com.cookey.weatherapplication.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import com.cookey.weatherapplication.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/** connection manager **/
@RequiresApi(Build.VERSION_CODES.M)
fun Context.isConnectedToTheInternet(): Boolean {
    val cnxManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val netInfo : Network? = cnxManager?.activeNetwork
    return netInfo != null
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatTodayOfTheWeek(value: String): String? {
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val outputFormat: DateFormat = SimpleDateFormat("EEEE HH:mm")
    val date: Date? = inputFormat.parse(value)
    val outputDateStr: String? = outputFormat.format(date!!)
    return  outputDateStr

}

fun convertKelvinToCelsius(degreeKelvin: Double): String {

    val tempResultInCelsius = degreeKelvin - 273.15
    val roundedValue = Math.rint(tempResultInCelsius)
    val temperatureCelsius = roundedValue.toInt().toString()

    return temperatureCelsius
}

fun updateWeatherIcon(condition: Int): Int {

    when (condition) {
        in 0..299 -> {
            return R.mipmap.rain
        }
        in 300..499 -> {
            return R.mipmap.rain
        }
        in 500..599 -> {
            return R.mipmap.rain
        }
        in 600..700 -> {
            return R.mipmap.partly_sunny
        }
        in 701..771 -> {
            return R.mipmap.partly_sunny
        }
        in 772..799 -> {
            return R.mipmap.partly_sunny
        }
        800 -> {
            return R.mipmap.clear
        }
        in 801..804 -> {
            return R.mipmap.partly_sunny
        }
        in 900..902 -> {
            return R.mipmap.rain
        }
        903 -> {
            return R.mipmap.partly_sunny
        }
        904 -> {
            return R.mipmap.clear
        }
        in 905..1000 -> {
            return R.mipmap.rain
        }
        else -> return 0
    }

}



fun updateImage(condition: Int): Int {

    when (condition) {
        in 0..299 -> {
            return R.drawable.sea_rainy
        }
        in 300..499 -> {
            return R.drawable.forest_rainy
        }
        in 500..599 -> {
            return R.drawable.forest_rainy
        }
        in 600..700 -> {
            return R.drawable.forest_cloudy
        }
        in 701..771 -> {
            return R.drawable.sea_cloudy
        }
        in 772..799 -> {
            return R.drawable.sea_cloudy
        }
        800 -> {
            return R.drawable.forest_sunny
        }
        in 801..804 -> {
            return R.drawable.forest_cloudy
        }
        in 900..902 -> {
            return R.drawable.sea_rainy
        }
        903 -> {
            return R.drawable.forest_cloudy
        }
        904 -> {
            return R.drawable.sea_sunnypng
        }
        in 905..1000 -> {
            return R.drawable.forest_rainy
        }
        else -> return R.drawable.weather_background
    }
}


fun updateColor(condition: Int): Int {

    when (condition) {
        in 0..299 -> {
            return R.color.rainy_color
        }
        in 300..499 -> {
            return R.color.rainy_color
        }
        in 500..599 -> {
            return R.color.rainy_color
        }
        in 600..700 -> {
            return R.color.cloudy_color
        }
        in 701..771 -> {
            return  R.color.cloudy_color
        }
        in 772..799 -> {
            return  R.color.cloudy_color
        }
        800 -> {
            return  R.color.sunny_color
        }
        in 801..804 -> {
            return R.color.cloudy_color
        }
        in 900..902 -> {
            return R.color.rainy_color
        }
        903 -> {
            return R.color.cloudy_color
        }
        904 -> {
            return R.color.sunny_color
        }
        in 905..1000 -> {
            return R.color.rainy_color
        }
        else -> return R.color.purple_500
    }
}




