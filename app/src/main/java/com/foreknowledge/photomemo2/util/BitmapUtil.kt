package com.foreknowledge.photomemo2.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream

/**
 * Create by Yeji on 27,April,2020.
 */
object BitmapUtil {
	fun bitmapToImageFile(context: Context, bitmap: Bitmap): String {
		val imageFile = FileUtil.createJpgFile(context)

		return compressBitmapToImageFile(imageFile.absolutePath, bitmap)
	}

	fun rotateAndCompressImage(filePath: String): String {
		val options = BitmapFactory.Options()
		if (File(filePath).length() > 1000000)
			options.inSampleSize = 2

		val rotatedBitmap = BitmapFactory.decodeFile(filePath, options).getRotatedBitmap(filePath)

		return compressBitmapToImageFile(filePath, rotatedBitmap)
	}

	private fun compressBitmapToImageFile(imagePath: String, bitmap: Bitmap): String {
		FileOutputStream(imagePath)
			.use {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 50, it)
				it.flush()

				return imagePath
			}
	}

	private fun Bitmap.getRotatedBitmap(photoPath: String): Bitmap {
		val exifInterface = ExifInterface(photoPath)
		val orientation: Int = exifInterface.getAttributeInt(
			ExifInterface.TAG_ORIENTATION,
			ExifInterface.ORIENTATION_UNDEFINED
		)

		return when (orientation) {
			ExifInterface.ORIENTATION_ROTATE_90 -> this.rotateImage(90f)
			ExifInterface.ORIENTATION_ROTATE_180 -> this.rotateImage(180f)
			ExifInterface.ORIENTATION_ROTATE_270 -> this.rotateImage(270f)
			else -> this
		}
	}

	private fun Bitmap.rotateImage(angle: Float): Bitmap {
		val matrix = Matrix()
		matrix.postRotate(angle)
		return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
	}
}