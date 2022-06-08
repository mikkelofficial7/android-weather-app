package com.android.mobile.weatherapp.model

import com.google.gson.annotations.SerializedName

class CommonError(
    var errorCode: Int? = null,
    var errorMessage: String? = null
)