package com.cookey.weatherapplication.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Clouds : Serializable {

    @SerializedName("all")
    @Expose
    var all: Int? = null

}
