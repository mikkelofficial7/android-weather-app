package com.android.mobile.weatherapp.extension

import java.util.*

fun Long.next3DaysTimeStamp() : Long {
    val next3Days =  Date(this + (2 * (1000 * 60 * 60 * 24)))
    return next3Days.time / 1000
}