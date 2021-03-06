package com.foreknowledge.photomemo2.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Created by Yeji on 30,April,2020.
 */
@Suppress("DEPRECATION")
object NetworkUtil {
	private var connectivityManager: ConnectivityManager? = null

	fun isConnected(context: Context): Boolean {
		connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		return checkConnection()
	}

	private fun checkConnection(): Boolean {
		val network = connectivityManager?.activeNetwork
		val capabilities = connectivityManager?.getNetworkCapabilities(network)

		capabilities?.let {
			return it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
					it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
		}

		return false
	}
}