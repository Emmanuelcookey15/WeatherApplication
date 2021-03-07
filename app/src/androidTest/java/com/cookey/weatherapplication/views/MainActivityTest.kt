package com.cookey.weatherapplication.views

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.cookey.weatherapplication.R
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest : TestCase() {

    @get:Rule
    var permissionRule
            = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_WIFI_STATE,
        android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE,
        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)

    private lateinit var instrumentationContext: Context

    private lateinit var activityScenario : ActivityScenario<MainActivity>


    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun test_launch() {
        Espresso.onView(withId(R.id.main_activity))
    }

    @Test
    fun test_that_temprature_header_display_the_text_loading_before_changing(){
        Espresso.onView(withId(R.id.weather_description))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.loading)))
    }


}