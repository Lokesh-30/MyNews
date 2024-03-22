package com.example.mynewsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.mynewsapp.NewsApp

/**
 * Internet Connectivity Checker
 */
fun checkForInternet(context: Context = NewsApp().applicationContext): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this) { it?.let { t -> action(t) } }
}