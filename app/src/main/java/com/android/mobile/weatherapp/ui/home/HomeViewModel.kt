package com.android.mobile.weatherapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.mobile.weatherapp.model.CityData
import com.android.mobile.weatherapp.model.CommonError
import com.android.mobile.weatherapp.network.NetworkCall
import com.android.mobile.weatherapp.network.NetworkInterface
import com.android.mobile.weatherapp.network.ResponseCallback
import com.android.mobile.weatherapp.state.FindWeatherState

class HomeViewModel : ViewModel() {
    var findCityLiveData: MutableLiveData<FindWeatherState> = MutableLiveData()

    fun findCity(cityName: String) {
        MutableLiveData<FindWeatherState>().apply {
            val request = NetworkCall.provideRequest(NetworkInterface::class.java)
                .findWeatherByCityName(city = cityName)

            NetworkCall.process(request, object : ResponseCallback<CityData> {
                override fun onSuccess(responseBody: CityData) {
                    findCityLiveData.value = FindWeatherState.successLoad(responseBody)
                }

                override fun onError(responseCommonError: CommonError) {
                    findCityLiveData.value = FindWeatherState.failedLoad(responseCommonError)
                }
            })
        }
    }

}