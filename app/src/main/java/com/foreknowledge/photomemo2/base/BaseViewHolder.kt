package com.foreknowledge.photomemo2.base

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import com.foreknowledge.photomemo2.BR

/**
 * Create by Yeji on 22,April,2020.
 */
abstract class BaseViewHolder<B: ViewDataBinding, T: Any>(
		@LayoutRes layoutResId: Int,
		parent: ViewGroup?
): RecyclerView.ViewHolder(
		LayoutInflater.from(parent?.context).inflate(layoutResId, parent, false)
) {
	private val tag = javaClass.simpleName
	private val binding: B = DataBindingUtil.bind(itemView)!!

	fun bind(item: T) {
		try {
			binding.run {
				setVariable(BR.item, item)
				executePendingBindings()
			}
		} catch (e: Exception) {
			Log.e(tag, e.message.toString())
		}
	}
}