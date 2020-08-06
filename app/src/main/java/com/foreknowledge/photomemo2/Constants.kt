package com.foreknowledge.photomemo2

/**
 * Created by Yeji on 26,April,2020.
 */
const val CLICK_INTERVAL = 1000L
const val EXTRA_MEMO_ID = "memo_id"
const val EXTRA_PHOTOS = "photos"
const val EXTRA_PHOTO_POSITION = "photo_position"

const val MAX_IMAGE_COUNT = 20

const val MSG_IMAGE_FULL = "이미지 첨부는 ${MAX_IMAGE_COUNT}개까지 가능합니다."

object RequestCode {
	const val CHOOSE_CAMERA_IMAGE = 101
}