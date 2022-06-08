package com.android.mobile.weatherapp.network

import com.android.mobile.weatherapp.constant.Base
import com.android.mobile.weatherapp.model.CityData
import com.android.mobile.weatherapp.model.CityDataDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkInterface {
    @GET("data/2.5/weather")
    fun findWeatherByCityName(
        @Query("q") city:String,
        @Query("appid") apikey:String = Base.API_KEY,
        @Query("units") units:String = "metric"): Call<CityData>

    @GET("data/2.5/forecast")
    fun findWeatherDetail(
        @Query("lat") lat:Double = 0.0,
        @Query("lon") lon:Double = 0.0,
        @Query("appid") apikey:String = Base.API_KEY): Call<CityDataDetail>
}