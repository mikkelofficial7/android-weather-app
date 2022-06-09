package com.android.mobile.weatherapp.extension

import java.util.*

fun Long.next3DaysTimeStamp() : Long {
    val next3Days =  Date(this + (4 * (1000 * 60 * 60 * 24)))
    return next3Days.time
}

fun Long.toSecond() : Long {
    return this * 1000
}