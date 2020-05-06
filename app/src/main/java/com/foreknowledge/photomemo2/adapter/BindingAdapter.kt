package com.foreknowledge.photomemo2.adapter

import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Create by Yeji on 22,April,2020.
 */

@BindingAdapter("bind_urlImage")
fun ImageView.bindUrlImage(url: String?) {
    if (url == null) return
    Glide.with(context)
        .load(url.split(",")[0])
        .into(this)
}

@BindingAdapter("bind_imagesCount")
fun TextView.bindImagesCount(url: String?) {
    text = url?.split(",")?.size.toString()
}

@BindingAdapter("bind_imagePath")
fun ImageView.bindImagePath(path: String?) {
    if (path == null) return
    setImageBitmap(BitmapFactory.decodeFile(path))
}