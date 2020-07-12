package com.foreknowledge.photomemo2.util.importer

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.foreknowledge.photomemo2.RequestCode
import com.foreknowledge.photomemo2.util.FileUtil
import java.io.File

/**
 * Create by Yeji on 27,April,2020.
 */
object CameraImporter {
	private lateinit var file: File

	fun switchToCamera(activity: Activity) {
		file = FileUtil.createJpgFileExternal(activity)

		// uri 데이터 형태 = content://{authority}/{파일의 디렉토리}/{파일명}
		// ex) content://com.foreknowledge.photomemo2.fileprovider/Pictures/sample_image.jpg
		val uri = FileProvider.getUriForFile(activity, "${activity.packageName}.fileprovider", file)

		Intent(MediaStore.ACTION_IMAGE_CAPTURE)
			.apply { putExtra(MediaStore.EXTRA_OUTPUT, uri) }
			.also { activity.startActivityForResult(it, RequestCode.CHOOSE_CAMERA_IMAGE) }
	}

	fun getFilePath() = file.absolutePath ?: ""
}