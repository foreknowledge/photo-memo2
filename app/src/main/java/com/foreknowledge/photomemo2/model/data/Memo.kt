package com.foreknowledge.photomemo2.model.data

/**
 * Created by Yeji on 22,April,2020.
 */
data class Memo (
	val id: Long,
	val title: String,
	val content: String,
	var photoPaths: String = ""
)