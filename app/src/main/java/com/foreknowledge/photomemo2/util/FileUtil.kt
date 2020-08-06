package com.foreknowledge.photomemo2.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Yeji on 27,April,2020.
 */
object FileUtil {
	fun createJpgFileExternal(context: Context): File {
		val externalDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
		return createJpgFile(externalDirectory)
	}

	fun createJpgFile(directory: File?): File {
		return File.createTempFile(getFilePrefix(), getFileSuffix(), directory)
	}

	fun getFilePrefix(): String {
		val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
		return "JPEG_${timeStamp}_"
	}

	fun getFileSuffix() = ".jpg"

	fun deleteFile(filePath: String) = File(filePath).delete()

	fun deleteFiles(filePaths: List<String>) {
		filePaths.forEach { filePath ->
			deleteFile(filePath)
		}
	}
}