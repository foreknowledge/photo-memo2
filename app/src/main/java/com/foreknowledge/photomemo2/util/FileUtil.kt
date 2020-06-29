package com.foreknowledge.photomemo2.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by Yeji on 27,April,2020.
 */
object FileUtil {
	fun createJpgFile(context: Context): File {
		val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
		val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
		return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
	}

	fun deleteFile(filePath: String) = File(filePath).delete()

	fun deleteFiles(filePaths: List<String>) {
		filePaths.forEach { filePath ->
			deleteFile(filePath)
		}
	}
}