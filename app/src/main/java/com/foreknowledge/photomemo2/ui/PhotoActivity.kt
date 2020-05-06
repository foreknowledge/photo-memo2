package com.foreknowledge.photomemo2.ui

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.foreknowledge.photomemo2.EXTRA_PHOTOS
import com.foreknowledge.photomemo2.EXTRA_PHOTO_POSITION
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.adapter.PhotoViewPagerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityPhotoBinding

class PhotoActivity : BaseActivity<ActivityPhotoBinding>(R.layout.activity_photo) {

	private val photoViewPagerAdapter = PhotoViewPagerAdapter(this)

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
			registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
				override fun onPageSelected(position: Int) {
					setPageNumber(position, totalImageCount)
				}
			})
		}
		setPageNumber(currentPosition, totalImageCount)
	}

	private fun setPageNumber(position: Int, totalCount: Int) {
		binding.indicatorText = "${position + 1} / $totalCount"
	}
}
