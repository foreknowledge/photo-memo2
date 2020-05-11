package com.foreknowledge.photomemo2.adapter

import androidx.recyclerview.widget.RecyclerView
import com.foreknowledge.photomemo2.MAX_IMAGE_COUNT
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.base.BaseRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseViewHolder
import com.foreknowledge.photomemo2.databinding.ItemPreviewBinding
import com.foreknowledge.photomemo2.listener.OnItemClickListener
import com.foreknowledge.photomemo2.listener.OnItemDragListener
import com.foreknowledge.photomemo2.listener.OnItemMoveListener
import com.foreknowledge.photomemo2.util.FileUtil

/**
 * Create by Yeji on 27,April,2020.
 */
class PreviewRecyclerAdapter
	: BaseRecyclerAdapter<String>(R.layout.item_preview), OnItemMoveListener {
	private val originalImgPaths: List<String> = items
	private val actionHistory = mutableListOf<Action>()

	private data class Action(val type: Int, val imagePath: String)

	private var onItemDragListener: OnItemDragListener =
		object : OnItemDragListener {
			override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
				// default drag listener: do nothing
			}
		}

	fun setOnItemDragListener(listener: (viewHolder: RecyclerView.ViewHolder) -> Unit) {
		this.onItemDragListener = object : OnItemDragListener {
			override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
				listener(viewHolder)
			}
		}
	}

	fun getImages(): List<String> {
		for (action in actionHistory)
			if (action.type == DELETE_IMAGE)
				FileUtil.deleteFile(action.imagePath)

		return items
	}

	fun restoreImages(): List<String> {
		for (action in actionHistory)
			if (action.type == ADD_IMAGE)
				FileUtil.deleteFile(action.imagePath)

		return originalImgPaths
	}

	fun addPath(path: String) {
		actionHistory.add(Action(ADD_IMAGE, path))
		items.add(path)
		notifyDataSetChanged()
	}

	fun addPaths(paths: List<String>) {
		paths.forEach {
			actionHistory.add(Action(ADD_IMAGE, it))
			items.add(it)
		}
		notifyDataSetChanged()
	}

	fun isFull() = itemCount == MAX_IMAGE_COUNT

	override fun onBindViewHolder(holder: BaseViewHolder<String>, position: Int) {
		holder.bind(items[position], object : OnItemClickListener<String>{
			override fun onClick(item: String) {
				actionHistory.add(Action(DELETE_IMAGE, item))
				items.remove(item)
				notifyDataSetChanged()
			}
		})

		(holder.binding as ItemPreviewBinding).imagePreview.setOnLongClickListener {
			onItemDragListener.onStartDrag(holder)
			true
		}
	}

	override fun onItemMove(fromPosition: Int, toPosition: Int) {
		val target = items[fromPosition]
		items.removeAt(fromPosition)
		items.add(toPosition, target)

		notifyItemMoved(fromPosition, toPosition)
	}

	companion object {
		const val ADD_IMAGE = 1
		const val DELETE_IMAGE = -1
	}
}