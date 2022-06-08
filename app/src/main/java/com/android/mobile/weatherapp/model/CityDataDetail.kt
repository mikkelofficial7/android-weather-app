package com.android.mobile.weatherapp.model

import com.google.gson.annotations.SerializedName

class CityDataDetail {
    @SerializedName("list")
    var temperatureList: ArrayList<TemperatureData>? = null
}

class TemperatureData {
    @SerializedName("dt")
    var timestamp: Long = 0
    @SerializedName("weather")
    var listWeather: ArrayList<Weather>? = null
    @SerializedName("main")
    var detailWeather: DetailWeather? = null
    @SerializedName("wind")
    var wind: Wind? = null
}

class Wind {
    @SerializedName("speed")
    var speed: Double? = null
    @SerializedName("deg")
    var deg: Double? = null
    @SerializedName("gust")
    var gust: Double? = null
}