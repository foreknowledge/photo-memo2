package com.foreknowledge.photomemo2.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Create by Yeji on 22,April,2020.
 */

@BindingAdapter("bind_url_image")
fun ImageView.bindUrlImage(url: String?) {
    Glide.with(context)
        .load(url?.split(",")?.get(0))
        .into(this)
}

@BindingAdapter("bind_images_count")
fun TextView.bindImagesCount(url: String?) {
    text = url?.split(",")?.size.toString()
}