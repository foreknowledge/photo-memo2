package com.foreknowledge.photomemo2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		Intent(this, MainActivity::class.java)
			.also { startActivity(it) }
		finish()
	}
}
