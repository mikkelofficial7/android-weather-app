package com.android.mobile.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.android.mobile.weatherapp.room.converter.CoordConverter
import com.android.mobile.weatherapp.room.converter.DetailWeatherConverter
import com.android.mobile.weatherapp.room.converter.WeatherConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city")
class CityData {
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    var cityId: Long = 0

    @TypeConverters(CoordConverter::class)
    @SerializedName("coord")
    var coord: Coord? = null

    @TypeConverters(WeatherConverter::class)
    @SerializedName("weather")
    var listWeather: ArrayList<Weather>? = null

    @TypeConverters(DetailWeatherConverter::class)
    @SerializedName("main")
    var detailWeather: DetailWeather? = null

    @SerializedName("name")
    var cityName: String = ""
}

class Coord {
    @SerializedName("lon")
    var lon: Double = 0.0

    @SerializedName("lat")
    var lat: Double = 0.0
}

class Weather {
    @SerializedName("main")
    var main: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("icon")
    var icon: String = ""
}

class DetailWeather {
    @SerializedName("temp")
    var temp: Double = 0.0

    @SerializedName("feels_like")
    var feels_like: Double = 0.0

    @SerializedName("temp_min")
    var temp_min: Double = 0.0

    @SerializedName("temp_max")
    var temp_max: Double = 0.0

    @SerializedName("pressure")
    var pressure: Double = 0.0

    @SerializedName("humidity")
    var humidity: Double = 0.0
}