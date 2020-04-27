package com.foreknowledge.photomemo2

/**
 * Create by Yeji on 26,April,2020.
 */
const val CLICK_INTERVAL = 1000L
const val EXTRA_MEMO_ID = "memo_id"

const val MAX_IMAGE_COUNT = 10

const val MSG_IMAGE_FULL = "이미지 첨부는 ${MAX_IMAGE_COUNT}개까지만 가능합니다."

object RequestCode {
	const val PERMISSION_REQUEST_CODE = 100
	const val CHOOSE_CAMERA_IMAGE = 101
	const val CHOOSE_GALLERY_IMAGE = 102
}