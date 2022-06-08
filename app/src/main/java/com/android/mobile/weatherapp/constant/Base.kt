package com.android.mobile.weatherapp.constant

import com.android.mobile.weatherapp.BuildConfig

interface Base {
    companion object {
        val BASE_URL: String = BuildConfig.BaseUrl
        val BASE_IMAGE_URL: String = BuildConfig.BaseImageUrl
        val API_KEY: String = BuildConfig.ApiKey
    }
}