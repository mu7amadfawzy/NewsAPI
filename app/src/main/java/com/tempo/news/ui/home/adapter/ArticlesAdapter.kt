package com.tempo.news.ui.home

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.tempo.news.R
import com.tempo.news.data.model.ArticleDM
import com.tempo.news.databinding.ArticleRowBinding
import com.tempo.news.ui.base.BaseLoaderAdapter
import com.tempo.news.ui.base.BaseViewHolder
import com.tempo.news.ui.home.details.ArticleDetailsActivity

open class ArticlesAdapter(
    val activity: Activity,
    val dataList: List<ArticleDM>,
    paginationHandler: PaginationHandler
) : BaseLoaderAdapter<ArticleRowBinding, ArticleDM>(R.layout.article_row, paginationHandler) {

    override fun onHolderCreated(holder: BaseViewHolder<ArticleRowBinding, ArticleDM>?): RecyclerView.ViewHolder {
        holder?.binding?.root?.setOnClickListener { v ->
            ArticleDetailsActivity.start(
                activity,
                v,
                holder.binding?.dataModel
            )
        }
        return super.onHolderCreated(holder)
    }

}