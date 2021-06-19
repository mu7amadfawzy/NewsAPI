package com.tempo.news.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tempo.news.R

object ImageLoader {
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_place_holder)
            )
            .into(view)

    }

}