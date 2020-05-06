package com.foreknowledge.photomemo2.adapter

import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.base.BaseRecyclerAdapter

/**
 * Create by Yeji on 28,April,2020.
 */
class PhotoRecyclerAdapter: BaseRecyclerAdapter<String>(R.layout.item_photo) {
    fun getItemPosition(photoPath: String) = items.indexOf(photoPath)
}