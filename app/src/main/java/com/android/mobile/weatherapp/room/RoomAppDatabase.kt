package com.android.mobile.weatherapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.mobile.weatherapp.model.CityData
import com.android.mobile.weatherapp.model.Coord
import com.android.mobile.weatherapp.model.DetailWeather
import com.android.mobile.weatherapp.model.Weather
import com.android.mobile.weatherapp.room.converter.CoordConverter
import com.android.mobile.weatherapp.room.converter.DetailWeatherConverter
import com.android.mobile.weatherapp.room.converter.WeatherConverter
import com.android.mobile.weatherapp.room.dao.CityDao

@Database(
    entities = [
        CityData::class
    ],
    version = 1
)

@TypeConverters(
    CoordConverter::class,
    WeatherConverter::class,
    DetailWeatherConverter::class
)

abstract class RoomAppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao?
}