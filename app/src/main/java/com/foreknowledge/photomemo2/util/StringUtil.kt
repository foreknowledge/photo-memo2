package com.foreknowledge.photomemo2.util

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.foreknowledge.photomemo2.GlobalApplication

/**
 * Create by Yeji on 26,April,2020.
 */
object StringUtil {
    fun getString(@StringRes resId: Int) =
        GlobalApplication.getContext().getString(resId)

    fun getStringArray(@ArrayRes resId: Int): Array<String> =
        GlobalApplication.getContext().resources.getStringArray(resId)
}