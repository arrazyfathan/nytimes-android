package com.arrazyfathan.nytimes.core.data.source.remote.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Created by Ar Razy Fathan Rabbani on 01/04/23.
 */

const val KEY_NO_INTERNET: Int = 444
const val KEY_UNAUTHORIZED: Int = 401
const val KEY_SERVER_ERROR: Int = 500

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
