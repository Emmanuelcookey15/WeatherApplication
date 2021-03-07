package com.cookey.weatherapplication.adapter

import android.content.Context
import android.content.Intent
import android.gesture.GestureLibrary
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cookey.weatherapplication.R
import com.cookey.weatherapplication.models.ForecastList
import com.cookey.weatherapplication.utils.convertKelvinToCelsius
import com.cookey.weatherapplication.utils.formatTodayOfTheWeek
import com.cookey.weatherapplication.utils.updateColor
import com.cookey.weatherapplication.utils.updateWeatherIcon
import com.google.gson.JsonObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class WeatherForecastAdapter(var ctx: Context) : RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastHolder>() {

    private var forecastList: List<ForecastList> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastHolder {
        return WeatherForecastHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.weather_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherForecastHolder, position: Int) {
        val forecastItem = forecastList[position]
        holder.tempDay.text = forecastItem.dtTxt?.let { formatTodayOfTheWeek(it) }
        forecastItem.weather?.get(0)?.id?.let {
            updateWeatherIcon(
                it
            )
        }?.let { holder.tempIcon.setImageResource(it) }

        holder.tempValue.text = forecastItem.main?.temp?.let { it1 -> convertKelvinToCelsius(it1) }

        holder.weatherItem.background = (forecastItem.weather?.get(0)?.id?.let { it1 -> updateColor(it1) }?.let { it2 ->
            ContextCompat.getColor(ctx,
                it2
            )
        }?.let { it3 -> ColorDrawable(it3) })



    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    fun setWeatherForecasts(forecastListing: List<ForecastList>) {
        this.forecastList = forecastListing
        notifyDataSetChanged()
    }

    class WeatherForecastHolder(itemView: View) : ViewHolder(itemView) {

        val tempDay = itemView.findViewById<TextView>(R.id.temp_text)
        val tempIcon = itemView.findViewById<ImageView>(R.id.temp_icon)
        val tempValue = itemView.findViewById<TextView>(R.id.temp_value)
        val weatherItem = itemView.findViewById<LinearLayout>(R.id.weather_item)

    }





}
