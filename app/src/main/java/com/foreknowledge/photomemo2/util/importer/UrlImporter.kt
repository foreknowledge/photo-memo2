package com.foreknowledge.photomemo2.util.importer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

/**
 * Create by Yeji on 27,April,2020.
 */
object UrlImporter {
	fun fadeIn(targetView: View) {
		targetView.apply {
			alpha = 0f
			visibility = View.VISIBLE
			animate()
					.alpha(1f)
					.setDuration(300L)
					.setListener(null)
		}
	}

	fun fadeOut(targetView: View) {
		targetView.animate()
				.alpha(0f)
				.setDuration(300L)
				.setListener(object : AnimatorListenerAdapter() {
					override fun onAnimationEnd(animation: Animator) {
						targetView.visibility = View.GONE
					}
				})
	}
}