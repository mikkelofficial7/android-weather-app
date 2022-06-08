package com.android.mobile.weatherapp.state

import com.android.mobile.weatherapp.model.CityData
import com.android.mobile.weatherapp.model.CityDataDetail
import com.android.mobile.weatherapp.model.CommonError

sealed class FindWeatherDetailState {
    data class successLoad(val dataCity: CityDataDetail): FindWeatherDetailState()
    data class failedLoad(val commonErrorCity: CommonError): FindWeatherDetailState()
}