package com.foreknowledge.photomemo2.util

import com.foreknowledge.photomemo2.GlobalApplication

/**
 * Create by Yeji on 26,April,2020.
 */
object StringUtil {
    fun getString(resId: Int) = GlobalApplication.getContext().getString(resId)
}