package com.foreknowledge.photomemo2.util.importer

import android.content.Context
import android.net.Uri
import android.util.Log
import com.foreknowledge.photomemo2.MSG_IMAGE_FULL
import com.foreknowledge.photomemo2.util.FileUtil
import gun0912.tedimagepicker.builder.TedImagePicker
import java.io.File

/**
 * Create by Yeji on 27,April,2020.
 */
object GalleryImporter {
	fun startMultiImage(
		context: Context,
		maxCount: Int,
		maxMessage: String = MSG_IMAGE_FULL,
		completed: (list: List<Uri>) -> Unit
	) {
		TedImagePicker.with(context)
			.showCameraTile(false)
			.max(maxCount, maxMessage)
			.errorListener { message -> Log.d(javaClass.simpleName, "error: $message") }
			.startMultiImage { list: List<Uri> -> completed(list) }
	}

	fun getFilePath(context: Context, data: Uri): String {
		data.path?.let {
			return copyImageAndReturnFilePath(context, it)
		}
		return ""
	}

	private fun copyImageAndReturnFilePath(context: Context, originalFilePath: String): String {
		val newFile = FileUtil.createJpgFileExternal(context)
		File(originalFilePath).copyTo(newFile, true)

		return newFile.absolutePath
	}
}