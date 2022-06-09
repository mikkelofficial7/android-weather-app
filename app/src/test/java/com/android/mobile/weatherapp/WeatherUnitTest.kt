package com.android.mobile.weatherapp

import com.android.mobile.weatherapp.model.CityData
import com.android.mobile.weatherapp.model.CityDataDetail
import com.android.mobile.weatherapp.network.NetworkCall
import com.android.mobile.weatherapp.network.NetworkInterface
import com.android.mobile.weatherapp.network.ResponseCallback
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherUnitTest {
    private lateinit var request: NetworkInterface

    @Before
    fun init() {
        request = NetworkCall.provideRequest(NetworkInterface::class.java)
    }

    @Test
    fun testFindWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            val validResponse = CityData()
            val findWeather = request.findWeatherByCityName("jakarta")

            NetworkCall.process(findWeather, object : ResponseCallback<CityData> {
                override fun onSuccess(responseBody: CityData) {

                    Mockito.`when`(responseBody).thenReturn(validResponse)
                    assertEquals("Result should be true", validResponse, responseBody)

                }
            })
        }
    }

    @Test
    fun testFindWeatherByLatLong() {
        CoroutineScope(Dispatchers.IO).launch {
            val validResponse = CityDataDetail()
            val findWeather = request.findWeatherByCityName("jakarta")

            NetworkCall.process(findWeather, object : ResponseCallback<CityData> {
                override fun onSuccess(responseBody: CityData) {

                    val findWeatherDetail = request.findWeatherDetail(responseBody.coord?.lat ?: 0.0, responseBody.coord?.lon ?: 0.0)

                    NetworkCall.process(findWeatherDetail, object : ResponseCallback<CityDataDetail> {
                        override fun onSuccess(responseBody: CityDataDetail) {

                            Mockito.`when`(responseBody).thenReturn(validResponse)
                            assertEquals("Result should be true", validResponse, responseBody)

                        }
                    })

                }
            })
        }
    }
}