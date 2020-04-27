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
		// TODO: 권한 체크

		file = FileUtil.createJpgFile(activity)
		val uri = FileProvider.getUriForFile(activity, "${activity.packageName}.fileprovider", file)

		val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

		activity.startActivityForResult(intent, RequestCode.CHOOSE_CAMERA_IMAGE)
	}

	fun getFilePath() = file.absolutePath ?: ""
}