package com.foreknowledge.photomemo2.util

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File

/**
 * Create by Yeji on 30,June,2020.
 */
object DownloadUtil {
    private const val DIRECTORY_NAME = "PhotoMemo"
    private const val MIME_TYPE = "image/*"

    private var filePath = ""
    private var directoryName = ""

    fun downloadImage(context: Context, filePath: String) {
        init(filePath)
        throwExceptionIfFilePathIsBlank()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            downloadFileHigherVersion(context)
        else
            downloadFileLowerVersion(context)
    }

    private fun init(filePath: String) {
        this.filePath = filePath

        if (directoryName.isBlank()) {
            this.directoryName = "${Environment.DIRECTORY_PICTURES}/$DIRECTORY_NAME"
        }
    }

    private fun throwExceptionIfFilePathIsBlank() {
        if (filePath.isBlank()) {
            throw IllegalArgumentException("file path is empty")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun downloadFileHigherVersion(context: Context) {
        val fileName = FileUtil.getFilePrefix()

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE)
            put(MediaStore.MediaColumns.RELATIVE_PATH, directoryName)
        }

        val resolver = context.contentResolver
        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let { uri ->
            resolver.openOutputStream(uri).use {
                Log.d("test", "Higher: ${uri.path} Stream opened.")
            }
        }
    }

    private fun downloadFileLowerVersion(context: Context) {
        val directory = Environment.getExternalStoragePublicDirectory(directoryName)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = FileUtil.createJpgFile(directory)
        File(filePath).copyTo(file, true)

        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            arrayOf(MIME_TYPE)
        ) { _, _ -> }
    }
}