package com.tempo.news.ui.home.details

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.tempo.news.R
import com.tempo.news.data.model.ArticleDM
import com.tempo.news.databinding.ActivityArticleDetailsBinding
import com.tempo.news.ui.base.BaseActivity
import com.tempo.news.utils.Extensions.startActivity


class ArticleDetailsActivity :
    BaseActivity<ArticleDetailsViewModel, ActivityArticleDetailsBinding>() {

    companion object {
        fun start(activity: Activity, view: View, dm: ArticleDM?) {
            val extras = Bundle()
            extras.putParcelable("dataModel", dm)
            activity.startActivity(view, ArticleDetailsActivity::class.java, extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configViewModel()
    }


    override fun setupViews() {
        initToolbar()
    }

    private fun configViewModel() {
        viewModel.readExtras(intent.extras)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }

    override fun getLayoutRes() = R.layout.activity_article_details
    override fun getViewModelClass() = ArticleDetailsViewModel::class.java
    override fun getViewBinding() = ActivityArticleDetailsBinding.inflate(layoutInflater)

}