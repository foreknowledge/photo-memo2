package com.foreknowledge.photomemo2.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.foreknowledge.photomemo2.listener.OnItemClickListener

/**
 * Create by Yeji on 22,April,2020.
 */
abstract class BaseRecyclerAdapter(
		@LayoutRes private val layoutResId: Int
) : RecyclerView.Adapter<BaseViewHolder>() {
	private var items = listOf<Any>()

	var onClickListener = object : OnItemClickListener {
		override fun onClick(item: Any) {
			// do nothing
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
			object : BaseViewHolder(layoutResId, parent) {}

	override fun getItemCount(): Int = items.size

	open fun replaceItems(newItems: List<Any>?) {
		if (newItems != null) {
			items = newItems
			notifyDataSetChanged()
		}
	}

	override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
		holder.bind(items[position], onClickListener)
}