package com.foreknowledge.photomemo2

import android.app.Application
import android.content.Context

/**
 * Create by Yeji on 22,April,2020.
 */
class GlobalApplication: Application() {
	companion object {
		private lateinit var APPLICATION_CONTEXT: Context

		@JvmStatic
		fun getContext(): Context {
			return APPLICATION_CONTEXT
		}
	}

	override fun onCreate() {
		super.onCreate()
		APPLICATION_CONTEXT = applicationContext
	}
}