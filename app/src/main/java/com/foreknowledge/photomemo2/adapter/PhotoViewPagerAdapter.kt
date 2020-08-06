package com.foreknowledge.photomemo2.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foreknowledge.photomemo2.util.StringDiffCallback
import com.github.chrisbanes.photoview.PhotoView

/**
 * Created by Yeji on 28,April,2020.
 */
class PhotoViewPagerAdapter(
    private val context: Context
) : ListAdapter<String, PhotoViewPagerAdapter.PhotoViewHolder>(StringDiffCallback()) {

    inner class PhotoViewHolder(val view: PhotoView): RecyclerView.ViewHolder(view)

    fun replaceItems(newItems: List<String>) = submitList(newItems)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val photoView = PhotoView(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        return PhotoViewHolder(photoView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.view.setImageBitmap(BitmapFactory.decodeFile(getItem(position)))
    }
}