package com.foreknowledge.photomemo2.util

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Yeji on 12,June,2020.
 */
class StringDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem.contentEquals(newItem)
}