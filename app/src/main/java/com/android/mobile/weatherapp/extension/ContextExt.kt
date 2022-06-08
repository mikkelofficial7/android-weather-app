package com.android.mobile.weatherapp.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi

fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        getConnectivityLegacy(networkInfo)
    } else {
        getConnectivity(networkInfo, connectivityManager)
    }
}

private fun getConnectivityLegacy(activeNetwork: NetworkInfo?): Boolean {
    var isConnect = false
    if (activeNetwork != null) {
        if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
            isConnect = true
        } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
            isConnect = true
        }
    } else {
        isConnect = false
    }
    return isConnect
}

@RequiresApi(api = Build.VERSION_CODES.M)
private fun getConnectivity(
    networkInfo: NetworkInfo?,
    connectivityManager: ConnectivityManager
): Boolean {
    val nc = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return if (nc != null) {
        val downSpeed = nc.linkDownstreamBandwidthKbps
        val upSpeed = nc.linkUpstreamBandwidthKbps
        val hasCapabilityNetwork = nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        networkInfo != null && networkInfo.isConnectedOrConnecting && hasCapabilityNetwork
    } else {
        networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}