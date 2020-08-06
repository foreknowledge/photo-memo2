package com.foreknowledge.photomemo2.util.importer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Yeji on 27,April,2020.
 */
object UrlImporter {
	private val coroutineScope = CoroutineScope(Dispatchers.Main)

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
			.setListener(object: AnimatorListenerAdapter() {
				override fun onAnimationEnd(animation: Animator) {
					targetView.visibility = View.GONE
				}
			})
	}

	fun convertBitmap(
		context: Context,
		url: String,
		success: (bitmap: Bitmap?) -> Unit,
		failed: () -> Unit
	) {
		Glide.with(context)
			.asBitmap()
			.load(url)
			.listener(object: RequestListener<Bitmap> {
				override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
					coroutineScope.launch { failed() }
					return false
				}

				override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
					coroutineScope.launch {
						if (resource == null) failed()
						else success(resource)
					}
					return false
				}
			}).submit()
	}
}