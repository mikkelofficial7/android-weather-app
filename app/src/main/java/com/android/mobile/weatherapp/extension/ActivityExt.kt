package com.android.mobile.weatherapp.extension

import android.app.Activity

fun Activity?.isSafe() : Boolean {
    return this != null && !this.isDestroyed && !this.isFinishing
}

inline fun <reified T : Activity> Activity?.checkSafetyActivity() : T? {
    if(this is T && this.isSafe()) {
        return this
    }
    return null
}