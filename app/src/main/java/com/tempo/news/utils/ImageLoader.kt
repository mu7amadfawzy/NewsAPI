package com.tempo.news.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .into(view)

    }

}