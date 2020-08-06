package com.foreknowledge.photomemo2.adapter

import androidx.recyclerview.widget.DiffUtil
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.base.BaseRecyclerAdapter
import com.foreknowledge.photomemo2.model.data.Memo

/**
 * Created by Yeji on 22,April,2020.
 */
class MemoRecyclerAdapter: BaseRecyclerAdapter<Memo>(R.layout.item_memo, diffCallback) {
    companion object {
        private val diffCallback = object: DiffUtil.ItemCallback<Memo>() {
            override fun areItemsTheSame(oldItem: Memo, newItem: Memo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Memo, newItem: Memo) =
                oldItem == newItem
        }
    }
}