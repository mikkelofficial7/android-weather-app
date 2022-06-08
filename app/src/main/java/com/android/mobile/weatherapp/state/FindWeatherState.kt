package com.android.mobile.weatherapp.state

import com.android.mobile.weatherapp.model.CityData
import com.android.mobile.weatherapp.model.CommonError

sealed class FindWeatherState {
    data class successLoad(val dataCity: CityData): FindWeatherState()
    data class failedLoad(val commonErrorCity: CommonError): FindWeatherState()
}