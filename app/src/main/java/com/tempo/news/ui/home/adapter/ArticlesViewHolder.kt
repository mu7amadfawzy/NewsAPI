package com.tempo.news.ui.home.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.tempo.news.data.model.ArticleDM
import com.tempo.news.databinding.ArticleRowBinding
import com.tempo.news.ui.home.details.ArticleDetailsActivity

class ArticlesViewHolder(val activity: Activity, private val itemBinding: ArticleRowBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    init {
        configClickListeners()
    }

    private fun configClickListeners() {
        itemBinding.root.setOnClickListener { v ->
            ArticleDetailsActivity.start(
                activity,
                v,
                itemBinding.dataModel
            )
        }
    }


    fun bind(dataModel: ArticleDM, position: Int) {
        itemBinding.dataModel = dataModel
        itemBinding.position = position
    }
}