package com.foreknowledge.photomemo2.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView

/**
 * Create by Yeji on 28,April,2020.
 */
class PhotoViewPagerAdapter(
    private val context: Context
) : RecyclerView.Adapter<PhotoViewPagerAdapter.PhotoViewHolder>() {
    private var items = listOf<String>()

    inner class PhotoViewHolder(val view: PhotoView): RecyclerView.ViewHolder(view)

    fun replaceItems(newItems: List<String>?) {
        if (newItems != null) {
            items = newItems
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val photoView = PhotoView(context)
        photoView.layoutParams = (ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        return PhotoViewHolder(photoView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.view.setImageBitmap(BitmapFactory.decodeFile(items[position]))
    }
}