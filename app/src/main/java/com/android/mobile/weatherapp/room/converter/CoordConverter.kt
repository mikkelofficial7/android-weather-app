package com.android.mobile.weatherapp.room.converter

import androidx.room.TypeConverter
import com.android.mobile.weatherapp.model.Coord
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CoordConverter {

    @TypeConverter
    @JvmStatic
    fun listToString(value: String?): Coord {
        return if (value == null) {
            Coord()
        } else {
            val listType = object : TypeToken<Coord?>() {}.type
            Gson().fromJson<Coord>(value, listType)
        }
    }

    @TypeConverter
    @JvmStatic
    fun stringToList(list: Coord?): String {
        return if (list == null) {
            ""
        } else {
            val gson = Gson()
            gson.toJson(list)
        }
    }
}