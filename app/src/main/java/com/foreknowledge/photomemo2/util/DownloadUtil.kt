package com.foreknowledge.photomemo2.util

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.foreknowledge.photomemo2.listener.OnDownloadListener
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.IllegalArgumentException

/**
 * Create by Yeji on 30,June,2020.
 */
object DownloadUtil {
    private const val DIRECTORY_NAME = "PhotoMemo"
    private const val MIME_TYPE = "image/*"

    private var filePath = ""
    private var directoryName = ""

    private var onDownloadListener: OnDownloadListener? = null

    fun downloadImage(context: Context, filePath: String, onSuccess: () -> Unit) {
        init(filePath, onSuccess)
        throwExceptionIfFilePathIsBlank()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            downloadFileHigherVersion(context)
        else
            downloadFileLowerVersion(context)
    }

    private fun init(filePath: String, onSuccess: () -> Unit) {
        this.filePath = filePath
        this.onDownloadListener = object: OnDownloadListener {
            override fun onSuccess() {
                onSuccess()
            }
        }

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
        val fileName = getNewFileName()

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE)
            put(MediaStore.MediaColumns.RELATIVE_PATH, directoryName)
        }

        val resolver = context.contentResolver
        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let { uri ->
            resolver.openOutputStream(uri).use {
                Files.copy(Paths.get(filePath), it)
            }
            onDownloadListener?.onSuccess()
        }
    }

    private fun getNewFileName(): String {
        val prefix = FileUtil.getFilePrefix()
        val suffix = FileUtil.getFileSuffix()
        return createTempFile(prefix, suffix).name
    }

    @Suppress("DEPRECATION")
    private fun downloadFileLowerVersion(context: Context) {
        val directory = Environment.getExternalStoragePublicDirectory(directoryName)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = FileUtil.createJpgFile(directory)

        copyTo(file)
        scanMedia(context, arrayOf(file.absolutePath))
    }

    private fun copyTo(newFile: File) {
        File(filePath).copyTo(newFile, true)
    }

    private fun scanMedia(context: Context, paths: Array<String>) {
        MediaScannerConnection.scanFile(context, paths, null) { _, _ ->
            onDownloadListener?.onSuccess()
        }
    }
}