package com.cookey.weatherapplication.views

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cookey.weatherapplication.R
import com.cookey.weatherapplication.adapter.WeatherForecastAdapter
import com.cookey.weatherapplication.utils.convertKelvinToCelsius
import com.cookey.weatherapplication.utils.isConnectedToTheInternet
import com.cookey.weatherapplication.utils.updateColor
import com.cookey.weatherapplication.utils.updateImage
import com.cookey.weatherapplication.viewmodels.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import java.security.AccessController.getContext

open class MainActivity : AppCompatActivity() {


    private lateinit var remoteConfig: FirebaseRemoteConfig
    private var viewModel: WeatherViewModel? = null

    internal var snackbar: Snackbar? = null
    internal var view: View? = null
    @Volatile
    private var isOn = false

    val REQUEST_CODE = 112

    // Time between location updates (5000 milliseconds or 5 seconds)
    val MIN_TIME: Long = 5000

    // Distance between location updates (1000m or 1km)
    val MIN_DISTANCE = 1000f

    //Location
    val LOCATION_PROVIDER = LocationManager.GPS_PROVIDER
    var mLocationManager : LocationManager? = null
    var mLocationListener: LocationListener? = null


    lateinit var progressBar: LinearLayout
    lateinit var temperatureValue: TextView
    lateinit var temperatureCurrent: TextView
    lateinit var temperatureMin: TextView
    lateinit var temperatureMax: TextView
    lateinit var weatherDescription: TextView
    lateinit var mainActivityLayout: ScrollView
    lateinit var imageViewLayout: RelativeLayout
    lateinit var maxMinCurrentBackground : LinearLayout
    lateinit var recyclerView: RecyclerView

    var weatherAdapter: WeatherForecastAdapter? = null

    var mActionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        temperatureValue = findViewById(R.id.temperature_value)
        temperatureCurrent = findViewById(R.id.temperature_current)
        temperatureMin = findViewById(R.id.temperature_min)
        temperatureMax = findViewById(R.id.temperature_max)
        weatherDescription = findViewById(R.id.weather_description)
        mainActivityLayout = findViewById(R.id.main_activity)
        imageViewLayout = findViewById(R.id.imageView)
        maxMinCurrentBackground = findViewById(R.id.layout_current_max_min)
        recyclerView = findViewById(R.id.rv)


        mActionBar = supportActionBar
        mActionBar?.setDisplayShowTitleEnabled(false);
        mActionBar?.setDisplayShowTitleEnabled(true);


        // Get Remote Config instance.
        // [START get_remote_config_instance]
        remoteConfig = Firebase.remoteConfig
        // [END get_remote_config_instance]

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        view = getView()
        if (view != null) {
            internetStatusNotification()
        }

        updateUI()
    }


    fun updateUI(){

        //Instantiate view model
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)


        viewModel?.errorCurrentData?.observe(this, Observer {
            snackBarfun(it)
        })




        viewModel?.currentWeatherData?.observe(this, Observer {

            if (it != null) {
                temperatureValue.text = it.main?.temp?.let { it1 -> convertKelvinToCelsius(it1) }
                temperatureCurrent.text = it.main?.temp?.let { it1 -> convertKelvinToCelsius(it1) }
                temperatureMax.text = it.main?.tempMax?.let { it1 -> convertKelvinToCelsius(it1) }
                temperatureMin.text =  it.main?.temp?.let { it1 -> convertKelvinToCelsius(it1) }
                weatherDescription.text = it.weather?.get(0)?.description?.capitalize()

                it.weather?.get(0)?.id?.let { it1 ->
                    updateImage(
                        it1
                    )
                }?.let { it2 -> imageViewLayout.setBackgroundResource(it2) }

                mainActivityLayout.background = (it.weather?.get(0)?.id?.let { it1 -> updateColor(it1) }?.let { it2 ->
                    ContextCompat.getColor(this,
                        it2
                    )
                }?.let { it3 -> ColorDrawable(it3) })

                maxMinCurrentBackground.background = (it.weather?.get(0)?.id?.let { it1 -> updateColor(it1) }?.let { it2 ->
                    ContextCompat.getColor(this,
                        it2
                    )
                }?.let { it3 -> ColorDrawable(it3) })

                mActionBar?.setBackgroundDrawable(it.weather?.get(0)?.id?.let { it1 -> updateColor(it1) }?.let { it2 ->
                    ContextCompat.getColor(this,
                        it2
                    )
                }?.let { it3 -> ColorDrawable(it3) })

            }

        })

        updateRecyclerView()

    }

    fun updateRecyclerView() {

        weatherAdapter = WeatherForecastAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = weatherAdapter

        viewModel?.errorForecastData?.observe(this, Observer {
            progressDialog(false)
            snackBarfun(it)
        })

        viewModel?.successfulForecastData?.observe(this, Observer {
            progressDialog(false)
            snackBarfun(it)
        })


        viewModel?.forecastWeatherData?.observe(this, Observer {
            it.list?.let { it1 -> weatherAdapter!!.setWeatherForecasts(it1) }
        })


    }



    override fun onResume() {
        super.onResume()
        registerInternetCheckReceiver()
        isOn = true
        getWeatherForCurrentLocation()
    }



    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
        super.onPause()
        isOn = false
    }



    private fun getWeatherForCurrentLocation() {
        Log.d("TELLUS", "getWeatherForCurrentLocation")
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        mLocationListener = object : LocationListener{
            override fun onLocationChanged(p0: Location) {

                val longitude = p0.longitude.toString()
                val latitude = p0.latitude.toString()

                Log.d("TELLUS", "longitude: $longitude")
                Log.d("TELLUS", "latitude: $latitude")

                progressDialog(true)
                viewModel?.postCurrentWeatherData(latitude, longitude)
                viewModel?.postForecastWeatherData(latitude, longitude)
            }

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

        }

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)

            return
        }
        mLocationManager!!.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE,
            mLocationListener as LocationListener
        )
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherForCurrentLocation()
            }
        }
    }


    fun progressDialog(bol: Boolean) {
        if (bol) {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE

        } else {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

        }
    }


    private fun snackBarfun(message: String) {
        if (view != null) {
            Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
        }
    }



    private fun internetStatusNotification() {
        snackbar =
            Snackbar.make(view!!, "Check your internet connection.", Snackbar.LENGTH_INDEFINITE)
        val snackBarView = snackbar!!.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(this,
                R.color.red
            ))
        val textView =
            snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.gravity = View.TEXT_ALIGNMENT_CENTER
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
    }



    private fun getView(): View? {
        val vg = findViewById<ViewGroup>(android.R.id.content)
        var rv: View? = null

        if (vg != null)
            rv = vg.getChildAt(0)
        if (rv == null)
            rv = window.decorView.rootView
        return rv
    }



    private fun registerInternetCheckReceiver() {
        val internetFilter = IntentFilter()
        internetFilter.addAction("android.net.wifi.STATE_CHANGE")
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(broadcastReceiver, internetFilter)
    }



    private val broadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context, intent: Intent) {
            if (isConnectedToTheInternet()) {
                snackbar?.dismiss()
            } else {
                snackbar?.show()
            }
        }
    }


}