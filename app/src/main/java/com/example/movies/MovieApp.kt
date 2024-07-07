package com.example.movies

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.movies.ui.util.networkCallback
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp : Application(){

    companion object {
        lateinit var instance: MovieApp
            private set
    }

    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreate() {
        super.onCreate()
        instance = this
        initNetworkConnectivity()

    }

    private fun initNetworkConnectivity() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager =
            applicationContext.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun onTerminate() {
        super.onTerminate()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    fun context(): Context = applicationContext
}