package com.android.mobile.weatherapp.network

import com.android.mobile.weatherapp.constant.ErrorCode
import com.android.mobile.weatherapp.model.CommonError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject




object NetworkCall {
    fun <T>process(call: Call<T>, responseCallback: ResponseCallback<T>) {

        val callback = object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                handleResponse(response, responseCallback)
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                responseCallback.onOtherError(throwable.message.toString(), throwable.localizedMessage.toString())
            }
        }

        call.enqueue(callback)
    }

    private fun <T>handleResponse(response: Response<T>, responseCallback: ResponseCallback<T>) {
        val responseBody = response.body() as T

        when(response.code()) {
            ErrorCode.CODE_SUCCESS -> responseCallback.onSuccess(responseBody)
            else -> {
                val objError = JSONObject(response.errorBody()!!.string())
                responseCallback.onError(
                    CommonError(
                        errorCode = objError.getInt("cod"),
                        errorMessage = objError.getString("message")
                    )
                )
            }
        }
    }

    fun <T>provideRequest(clazz: Class<T>) : T {
        return NetworkRequest.create(clazz)
    }
}