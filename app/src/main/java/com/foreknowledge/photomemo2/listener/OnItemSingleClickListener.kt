package com.foreknowledge.photomemo2.listener

import android.os.SystemClock
import com.foreknowledge.photomemo2.CLICK_INTERVAL

/**
 * Create by Yeji on 26,April,2020.
 */
abstract class OnItemSingleClickListener<T>(
		private val clickInterval: Long = CLICK_INTERVAL
): OnItemClickListener<T> {
	private var mLastClickTime = 0L

	override fun onClick(item: T) {
		// 중복 클릭 방지
		if (SystemClock.elapsedRealtime() - mLastClickTime < clickInterval) return
		mLastClickTime = SystemClock.elapsedRealtime()

		onSingleClick(item)
	}

	abstract fun onSingleClick(item: T)
}