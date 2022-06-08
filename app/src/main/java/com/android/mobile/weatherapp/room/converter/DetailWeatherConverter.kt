package com.android.mobile.weatherapp.room.converter

import androidx.room.TypeConverter
import com.android.mobile.weatherapp.model.Coord
import com.android.mobile.weatherapp.model.DetailWeather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DetailWeatherConverter {

    @TypeConverter
    @JvmStatic
    fun listToString(value: String?): DetailWeather {
        return if (value == null) {
            DetailWeather()
        } else {
            val listType = object : TypeToken<DetailWeather?>() {}.type
            Gson().fromJson<DetailWeather>(value, listType)
        }
    }

    @TypeConverter
    @JvmStatic
    fun stringToList(list: DetailWeather?): String {
        return if (list == null) {
            ""
        } else {
            val gson = Gson()
            gson.toJson(list)
        }
    }
}