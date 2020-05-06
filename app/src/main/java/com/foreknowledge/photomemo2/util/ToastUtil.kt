package com.foreknowledge.photomemo2.util

import android.content.Context
import android.widget.Toast
import com.foreknowledge.photomemo2.GlobalApplication

/**
 * Create by Yeji on 26,April,2020.
 */
object ToastUtil {
    fun showToast(msg: String, context: Context = GlobalApplication.getContext()) =
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}