package com.foreknowledge.photomemo2.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Create by Yeji on 22,April,2020.
 */

@BindingAdapter("bind_thumbnail")
fun ImageView.bindThumbnail(url: String?) {
    if (url == null) return

    val firstUrl = url.split(",")[0]
    Glide.with(this)
        .load(firstUrl)
        .into(this)
}

@BindingAdapter("bind_imagesCount")
fun TextView.bindImagesCount(url: String?) {
    text = url?.split(",")?.size.toString()
}

@BindingAdapter("bind_imagePath")
fun ImageView.bindImagePath(path: String?) {
    if (path == null) return
    Glide.with(this)
        .load(path)
        .into(this)
}