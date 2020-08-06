package com.foreknowledge.photomemo2

import android.app.Application
import android.content.Context

/**
 * Created by Yeji on 22,April,2020.
 */
class GlobalApplication: Application() {
	override fun onCreate() {
		super.onCreate()
		APPLICATION_CONTEXT = applicationContext
	}

	companion object {
		private lateinit var APPLICATION_CONTEXT: Context

		@JvmStatic
		fun getContext(): Context {
			return APPLICATION_CONTEXT
		}
	}
}