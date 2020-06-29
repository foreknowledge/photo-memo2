package com.foreknowledge.photomemo2.ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.foreknowledge.photomemo2.listener.OnItemMoveListener

/**
 * Create by Yeji on 29,April,2020.
 */
class PreviewItemTouchCallback(
		private val onItemMoveListener: OnItemMoveListener
): ItemTouchHelper.Callback() {

	override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
		val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
		return makeMovementFlags(dragFlags, 0)
	}

	override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
		onItemMoveListener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
		return true
	}

	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
}