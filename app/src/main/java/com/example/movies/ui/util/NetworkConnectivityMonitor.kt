package com.example.movies.ui.util
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

private val TAG = "networkCallback"

val networkCallback = object : ConnectivityManager.NetworkCallback() {
    // network is available for use
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        NetworkState.setNetworkState(isOnline = true)
    }

    // Network capabilities have changed for the network
    override fun onCapabilitiesChanged(
        network: Network,
        networkCapabilities: NetworkCapabilities
    ) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        val unmetered =
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
    }

    // lost network connection
    override fun onLost(network: Network) {
        super.onLost(network)
        NetworkState.setNetworkState(isOnline = false)

    }
}