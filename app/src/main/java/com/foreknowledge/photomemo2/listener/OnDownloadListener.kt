package com.foreknowledge.photomemo2.listener

import android.net.Uri

/**
 * Create by Yeji on 30,June,2020.
 */
interface OnDownloadListener {
    fun onSuccess(imagePath: String, uri: Uri)
}