package com.foreknowledge.photomemo2.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foreknowledge.photomemo2.MAX_IMAGE_COUNT
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.base.BaseViewHolder
import com.foreknowledge.photomemo2.databinding.ItemPreviewBinding
import com.foreknowledge.photomemo2.listener.OnItemClickListener
import com.foreknowledge.photomemo2.listener.OnItemDragListener
import com.foreknowledge.photomemo2.listener.OnItemMoveListener
import com.foreknowledge.photomemo2.util.FileUtil

/**
 * Created by Yeji on 27,April,2020.
 */
class PreviewRecyclerAdapter : RecyclerView.Adapter<BaseViewHolder<String>>(), OnItemMoveListener {
	private val items = mutableListOf<String>()

	private var onItemDragListener: OnItemDragListener? = null

	fun setOnItemDragListener(listener: (viewHolder: RecyclerView.ViewHolder) -> Unit) {
		this.onItemDragListener = object: OnItemDragListener {
			override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
				listener(viewHolder)
			}
		}
	}

	fun getImages(): List<String> = items.toList()

	fun addPath(path: String) {
		items.add(path)
		notifyDataSetChanged()
	}

	fun addPaths(paths: List<String>) {
		paths.forEach { items.add(it) }
		notifyDataSetChanged()
	}

	fun isFull() = itemCount == MAX_IMAGE_COUNT

	override fun getItemCount() = items.size

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		object: BaseViewHolder<String>(R.layout.item_preview, parent) {}

	override fun onBindViewHolder(holder: BaseViewHolder<String>, position: Int) {
		holder.bind(items[position], object: OnItemClickListener<String>{
			override fun onClick(item: String) {
				items.remove(item)
				notifyDataSetChanged()
			}
		})

		(holder.binding as ItemPreviewBinding).imagePreview.setOnLongClickListener {
			onItemDragListener?.onStartDrag(holder)
			true
		}
	}

	override fun onItemMove(fromPosition: Int, toPosition: Int) {
		val target = items[fromPosition]
		items.removeAt(fromPosition)
		items.add(toPosition, target)

		notifyItemMoved(fromPosition, toPosition)
	}

	fun replaceItems(newItems: List<String>) {
		items.clear()
		items.addAll(newItems)
		notifyDataSetChanged()
	}
}

