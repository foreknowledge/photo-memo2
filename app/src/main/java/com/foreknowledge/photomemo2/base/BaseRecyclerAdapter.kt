package com.foreknowledge.photomemo2.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.foreknowledge.photomemo2.listener.OnItemClickListener
import com.foreknowledge.photomemo2.listener.OnItemSingleClickListener

/**
 * Created by Yeji on 22,April,2020.
 */
abstract class BaseRecyclerAdapter<T>(
		@LayoutRes private val layoutResId: Int,
		diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<T>>(diffCallback) {

	private var onItemClickListener: OnItemClickListener<T>? = null

	open fun setOnItemClickListener(listener:(item: T) -> Unit) {
		this.onItemClickListener = object: OnItemSingleClickListener<T>() {
			override fun onSingleClick(item: T) {
				listener(item)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		object: BaseViewHolder<T>(layoutResId, parent) {}

	open fun replaceItems(newItems: List<T>) = submitList(newItems)

	override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
		holder.bind(getItem(position), onItemClickListener)
}