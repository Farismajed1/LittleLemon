package com.example.littlelemon.functions.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

// <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
//Check if network is available
fun isNetworkAvailable(context: Context): Boolean {
    //Get the connectivity manager manager, which is let the app check network state
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //Get the currently active network (could be WI-FI, Mobile data or ETHERNET)
    val network = connectivityManager.activeNetwork ?: return false
    //Get what kind of network is currently active
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    //Check the network type
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true // WI-FI
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true // Mobile data
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true // ETHERNET
        else -> false // No valid network connection
    }
}