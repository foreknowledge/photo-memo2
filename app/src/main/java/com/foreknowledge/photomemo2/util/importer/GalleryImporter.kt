package com.foreknowledge.photomemo2.util.importer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.foreknowledge.photomemo2.RequestCode
import com.foreknowledge.photomemo2.util.FileUtil
import java.io.File

/**
 * Create by Yeji on 27,April,2020.
 */
object GalleryImporter {
	fun switchToAlbum(activity: Activity) {
		val intent = Intent(Intent.ACTION_PICK)
		intent.type = MediaStore.Images.Media.CONTENT_TYPE

		activity.startActivityForResult(intent, RequestCode.CHOOSE_GALLERY_IMAGE)
	}

	fun getImageFilePath(activity: Activity, data: Uri): String {
		data.path?.let {
			activity.contentResolver.query(data, null, null, null, null)
					.use {
						it?.let{
							it.moveToNext()

							return copyImage(activity, it.getString(it.getColumnIndex("_data")))
						}
					}
		}
		return ""
	}

	private fun copyImage(activity: Activity, originalFilePath: String): String {
		val newFile = FileUtil.createJpgFile(activity)
		File(originalFilePath).copyTo(newFile, true)

		return newFile.absolutePath
	}
}