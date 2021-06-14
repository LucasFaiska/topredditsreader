package com.lfaiska.topredditsreader.presentation.components

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String) {
            Glide.with(view.context).load(url).into(view)
        }
    }
}