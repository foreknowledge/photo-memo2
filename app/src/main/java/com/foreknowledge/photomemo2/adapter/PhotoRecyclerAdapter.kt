package com.foreknowledge.photomemo2.adapter

import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.base.BaseRecyclerAdapter
import com.foreknowledge.photomemo2.util.StringDiffCallback

/**
 * Create by Yeji on 28,April,2020.
 */
class PhotoRecyclerAdapter: BaseRecyclerAdapter<String>(R.layout.item_photo, StringDiffCallback()) {
    fun getItemPosition(photoPath: String) = currentList.indexOf(photoPath)
}