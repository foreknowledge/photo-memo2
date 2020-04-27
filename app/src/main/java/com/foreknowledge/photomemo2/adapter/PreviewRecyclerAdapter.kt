package com.foreknowledge.photomemo2.adapter

import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.base.BaseRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseViewHolder
import com.foreknowledge.photomemo2.listener.OnItemClickListener
import com.foreknowledge.photomemo2.util.FileUtil

/**
 * Create by Yeji on 27,April,2020.
 */
class PreviewRecyclerAdapter: BaseRecyclerAdapter<String>(R.layout.item_preview) {
	companion object {
		const val MAX_IMAGE_COUNT = 10

		const val ADD_IMAGE = 1
		const val DELETE_IMAGE = -1
	}

	data class PhotoHistory(val type: Int, val imagePath: String)

	private val originalImgPaths: List<String> = items
	private val history = mutableListOf<PhotoHistory>()

	fun getImages(): List<String> {
		for (action in history)
			when (action.type) {
				DELETE_IMAGE -> FileUtil.deleteFile(action.imagePath)
			}

		return items
	}

	fun restoreImages(): List<String> {
		for (action in history)
			when (action.type) {
				ADD_IMAGE -> FileUtil.deleteFile(action.imagePath)
			}

		return originalImgPaths
	}

	fun addPath(path: String) {
		history.add(PhotoHistory(ADD_IMAGE, path))
		items.add(path)
		notifyDataSetChanged()
	}

	fun isFull() = itemCount == MAX_IMAGE_COUNT

	override fun onBindViewHolder(holder: BaseViewHolder<String>, position: Int) {
		holder.bind(items[position], object: OnItemClickListener<String>{
			override fun onClick(item: String) {
				history.add(PhotoHistory(DELETE_IMAGE, item))
				items.remove(item)
				notifyDataSetChanged()
			}
		})
	}
}