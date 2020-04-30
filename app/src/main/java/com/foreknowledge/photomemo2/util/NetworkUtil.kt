package com.foreknowledge.photomemo2.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * Create by Yeji on 30,April,2020.
 */
@Suppress("DEPRECATION")
object NetworkUtil {
	fun isConnected(context: Context): Boolean {
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			val network = connectivityManager.activeNetwork
			val capabilities = connectivityManager.getNetworkCapabilities(network)

			capabilities?.let {
				return it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
						it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
			}
		}
		else {
			connectivityManager.activeNetworkInfo?.let {
				return it.isConnectedOrConnecting
			}
		}

		return false
	}
}