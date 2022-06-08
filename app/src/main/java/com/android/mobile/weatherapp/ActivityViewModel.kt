package com.android.mobile.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.mobile.weatherapp.model.CityData
import com.android.mobile.weatherapp.model.CityDataDetail
import com.android.mobile.weatherapp.model.CommonError
import com.android.mobile.weatherapp.network.NetworkCall
import com.android.mobile.weatherapp.network.NetworkInterface
import com.android.mobile.weatherapp.network.ResponseCallback
import com.android.mobile.weatherapp.state.FindWeatherDetailState

class ActivityViewModel : ViewModel() {
    var findCityLiveData: MutableLiveData<FindWeatherDetailState> = MutableLiveData()

    fun findCity(latitude: Double, longitude: Double) {
        MutableLiveData<FindWeatherDetailState>().apply {
            val request = NetworkCall.provideRequest(NetworkInterface::class.java)
                .findWeatherDetail(lat = latitude, lon = longitude)

            NetworkCall.process(request, object : ResponseCallback<CityDataDetail> {
                override fun onSuccess(responseBody: CityDataDetail) {
                    findCityLiveData.value = FindWeatherDetailState.successLoad(responseBody)
                }

                override fun onError(responseCommonError: CommonError) {
                    findCityLiveData.value = FindWeatherDetailState.failedLoad(responseCommonError)
                }
            })
        }
    }
}