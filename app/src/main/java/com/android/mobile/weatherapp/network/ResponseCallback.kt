package com.android.mobile.weatherapp.network

import com.android.mobile.weatherapp.model.CommonError


interface ResponseCallback<T>{

    fun onSuccess(responseBody: T) = Unit

    fun onError(responseCommonError: CommonError) = Unit

    fun onOtherError(error: String, message: String) = Unit
}