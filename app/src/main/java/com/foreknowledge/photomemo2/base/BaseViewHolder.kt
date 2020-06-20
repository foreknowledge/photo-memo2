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
import com.foreknowledge.photomemo2.listener.OnItemClickListener

/**
 * Create by Yeji on 22,April,2020.
 */
abstract class BaseViewHolder<T>(
		@LayoutRes layoutResId: Int,
		parent: ViewGroup
) : RecyclerView.ViewHolder(
		LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
) {
	private val tag = javaClass.simpleName
	val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

	fun bind(item: T, listener: OnItemClickListener<T>?) {
		try {
			binding.run {
				setVariable(BR.item, item)
				setVariable(BR.listener, listener)
				executePendingBindings()
			}
		} catch (e: Exception) {
			Log.e(tag, e.message.toString())
		}
	}
}