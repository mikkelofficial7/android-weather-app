package com.android.mobile.weatherapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.mobile.weatherapp.model.CityData

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(cityData: CityData?)

    @Query("SELECT * from city where cityId = :cityId")
    fun getFavoriteCityById(cityId: Long?) : CityData?

    @Query("DELETE FROM city where cityId = :cityId")
    fun deleteFavoriteById(cityId: Long?)

    @Query("SELECT * FROM city")
    fun getAllCity() : List<CityData>?
}