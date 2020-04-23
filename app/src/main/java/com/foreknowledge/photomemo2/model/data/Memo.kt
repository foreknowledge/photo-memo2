package com.foreknowledge.photomemo2.model.data

/**
 * Create by Yeji on 22,April,2020.
 */
data class Memo (
    val title: String,
    val content: String,
    var photoPaths: String = "",
    val id: Long = 0L
)