package com.foreknowledge.photomemo2.util.importer

import android.content.Context
import android.net.Uri
import android.util.Log
import com.foreknowledge.photomemo2.MSG_IMAGE_FULL
import com.foreknowledge.photomemo2.util.FileUtil
import gun0912.tedimagepicker.builder.TedImagePicker
import java.io.File

/**
 * Created by Yeji on 27,April,2020.
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

	fun copyFile(context: Context, data: Uri): String {
		data.path?.let {
			val newFile = FileUtil.createJpgFileExternal(context)
			File(it).copyTo(newFile, true)

			return newFile.absolutePath
		}
		return ""
	}
}