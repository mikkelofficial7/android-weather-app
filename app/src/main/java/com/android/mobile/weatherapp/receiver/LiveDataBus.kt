package com.android.mobile.weatherapp.receiver

import android.app.Activity
import com.android.mobile.weatherapp.DetailActivity
import com.android.mobile.weatherapp.MainActivity
import com.android.mobile.weatherapp.extension.checkSafetyActivity

object LivedataBus {
    fun showNoNetwork(isConnected: Boolean, activity: Activity) {
        activity.checkSafetyActivity<MainActivity>().apply {
            if (this == null) return@apply

            if(isConnected) this.hideNoNetworkUI() else this.showNoNetworkUI()
        }

        activity.checkSafetyActivity<DetailActivity>().apply {
            if (this == null) return@apply

            if(isConnected) this.hideNoNetworkUI() else this.showNoNetworkUI()
        }
    } }