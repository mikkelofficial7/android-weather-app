package com.android.mobile.weatherapp.room.converter

import androidx.room.TypeConverter
import com.android.mobile.weatherapp.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object WeatherConverter {

    @TypeConverter
    @JvmStatic
    fun listToString(value: String?): ArrayList<Weather>? {
        return if (value == null) {
            arrayListOf()
        } else {
            val listType = object : TypeToken<ArrayList<Weather>?>() {}.type
            Gson().fromJson<ArrayList<Weather>?>(value, listType)
        }
    }

    @TypeConverter
    @JvmStatic
    fun stringToList(list: ArrayList<Weather>?): String {
        return if (list == null) {
            ""
        } else {
            val gson = Gson()
            gson.toJson(list)
        }
    }
}