package com.foreknowledge.photomemo2

/**
 * Create by Yeji on 26,April,2020.
 */
const val CLICK_INTERVAL = 1000L
const val EXTRA_MEMO_ID = "memo_id"

const val MAX_IMAGE_COUNT = 10

const val MSG_IMAGE_FULL = "이미지 첨부는 ${MAX_IMAGE_COUNT}개까지만 가능합니다."
const val MSG_VACANT_URL = "Url 주소를 입력해 주세요."
const val MSG_NETWORK_DISCONNECT = "네트워크 연결 상태를 확인 해 주세요."
const val MSG_VACANT_CONTENT = "입력한 내용이 없어 메모를 저장하지 않았어요."

object RequestCode {
	const val PERMISSION_REQUEST_CODE = 100
	const val CHOOSE_CAMERA_IMAGE = 101
	const val CHOOSE_GALLERY_IMAGE = 102
}