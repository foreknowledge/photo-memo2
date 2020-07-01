package com.foreknowledge.photomemo2.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.foreknowledge.photomemo2.EXTRA_PHOTOS
import com.foreknowledge.photomemo2.EXTRA_PHOTO_POSITION
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.adapter.PhotoViewPagerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityPhotoBinding
import com.foreknowledge.photomemo2.util.DownloadUtil
import com.foreknowledge.photomemo2.util.PermissionUtil
import com.foreknowledge.photomemo2.util.StringUtil
import com.foreknowledge.photomemo2.util.ToastUtil

@Suppress("UNUSED_PARAMETER")
class PhotoActivity : BaseActivity<ActivityPhotoBinding>(R.layout.activity_photo) {

	private val photoViewPagerAdapter = PhotoViewPagerAdapter(this)
	private var currentPosition: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.lifecycleOwner = this
		binding.goBefore.setOnClickListener { finish() }

		intent.getStringExtra(EXTRA_PHOTOS)?.let {
			photoViewPagerAdapter.replaceItems(it.split(","))
			setPhotoViewPager()
		}
	}

	private fun setPhotoViewPager() {
		val currentPosition = intent.getIntExtra(EXTRA_PHOTO_POSITION, 0)
		val totalImageCount = photoViewPagerAdapter.itemCount

		with (binding.photoViewPager) {
			adapter = photoViewPagerAdapter
			orientation = ViewPager2.ORIENTATION_HORIZONTAL
			setCurrentItem(currentPosition, false)
			registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
				override fun onPageSelected(position: Int) {
					setPageNumber(position, totalImageCount)
				}
			})
		}
		setPageNumber(currentPosition, totalImageCount)
	}

	private fun setPageNumber(position: Int, totalCount: Int) {
		binding.indicatorText = "${position + 1} / $totalCount"
		currentPosition = position
	}

	fun downloadImage(view: View) {
		// 권한 체크
		if (PermissionUtil.isPermissionGranted(this)) {
			downloadImageWithHandling()
		} else {
			Log.d(TAG, "downloadImage(): Permission denied")
			ToastUtil.showToast(StringUtil.getString(R.string.err_permission_denied), this)
		}
	}

	private fun downloadImageWithHandling() {
		try {
			val filePath = photoViewPagerAdapter.currentList[currentPosition]
			DownloadUtil.downloadImage(this, filePath) { showToastOnMain() }
		} catch (e: IllegalArgumentException) {
			Log.d(TAG, "download Image Error: ${e.message}")
			ToastUtil.showToast(StringUtil.getString(R.string.download_fail), this)
		}
	}

	private fun showToastOnMain() {
		runOnUiThread {
			ToastUtil.showToast(StringUtil.getString(R.string.msg_save), this)
		}
	}

	companion object {
		private const val TAG = "PhotoActivity"
	}
}
