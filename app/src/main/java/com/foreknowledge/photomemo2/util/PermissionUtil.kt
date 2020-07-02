package com.foreknowledge.photomemo2.util

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * Create by Yeji on 29,April,2020.
 */
object PermissionUtil {
	private fun getPermissions(context: Context) = listOf(
		ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE),
		ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
	)

	@TargetApi(Build.VERSION_CODES.M)
	fun isPermissionDenied(context: Context): Boolean {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
			for (permission in getPermissions(context))
				if (permission != PackageManager.PERMISSION_GRANTED) return true

		return false
	}

	@TargetApi(Build.VERSION_CODES.M)
	fun isPermissionGranted(context: Context): Boolean {
		return !isPermissionDenied(context)
	}
}