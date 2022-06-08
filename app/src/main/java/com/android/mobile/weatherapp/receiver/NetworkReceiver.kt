package com.android.mobile.weatherapp.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.android.mobile.weatherapp.extension.isNetworkConnected

class NetworkReceiver(private val activity: Activity): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        LivedataBus.showNoNetwork(activity.isNetworkConnected(), activity)
    }
}