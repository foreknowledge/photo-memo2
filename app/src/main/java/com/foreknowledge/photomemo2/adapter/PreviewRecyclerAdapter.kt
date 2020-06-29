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
 * Create by Yeji on 27,April,2020.
 */
class PreviewRecyclerAdapter : RecyclerView.Adapter<BaseViewHolder<String>>(), OnItemMoveListener {
	private val items = mutableListOf<String>()
	private val actionHistory = mutableListOf<Action>()

	private data class Action(val type: Int, val imagePath: String)

	private var onItemDragListener: OnItemDragListener? = null

	fun setOnItemDragListener(listener: (viewHolder: RecyclerView.ViewHolder) -> Unit) {
		this.onItemDragListener = object: OnItemDragListener {
			override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
				listener(viewHolder)
			}
		}
	}

	fun getImages(): List<String> {
		for (action in actionHistory)
			if (action.type == FLAG_DELETE_IMAGE)
				FileUtil.deleteFile(action.imagePath)

		return items
	}

	fun restoreImages(): List<String> {
		for (action in actionHistory)
			if (action.type == FLAG_ADD_IMAGE)
				FileUtil.deleteFile(action.imagePath)

		return items
	}

	fun addPath(path: String) {
		actionHistory.add(Action(FLAG_ADD_IMAGE, path))
		items.add(path)
		notifyDataSetChanged()
	}

	fun addPaths(paths: List<String>) {
		paths.forEach {
			actionHistory.add(Action(FLAG_ADD_IMAGE, it))
			items.add(it)
		}
		notifyDataSetChanged()
	}

	fun isFull() = itemCount == MAX_IMAGE_COUNT

	override fun getItemCount() = items.size

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		object: BaseViewHolder<String>(R.layout.item_preview, parent) {}

	override fun onBindViewHolder(holder: BaseViewHolder<String>, position: Int) {
		holder.bind(items[position], object: OnItemClickListener<String>{
			override fun onClick(item: String) {
				actionHistory.add(Action(FLAG_DELETE_IMAGE, item))
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

	companion object {
		const val FLAG_ADD_IMAGE = 1
		const val FLAG_DELETE_IMAGE = -1
	}
}

