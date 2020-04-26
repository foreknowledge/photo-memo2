package com.foreknowledge.photomemo2.util

import android.widget.Toast
import com.foreknowledge.photomemo2.GlobalApplication

/**
 * Create by Yeji on 26,April,2020.
 */
object ToastUtil {
    fun makeToast(msg: String) = Toast.makeText(GlobalApplication.getContext(), msg, Toast.LENGTH_SHORT).show()
}