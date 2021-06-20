package com.tempo.news.ui.home.details

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.tempo.news.data.model.ArticleDM
import com.tempo.news.databinding.ActivityArticleDetailsBinding
import com.tempo.news.ui.base.BaseActivity
import com.tempo.news.utils.Extensions.startActivity
import dagger.android.support.DaggerAppCompatActivity


class ArticleDetailsActivity : BaseActivity<ArticleDetailsViewModel>() {

    private lateinit var binding: ActivityArticleDetailsBinding

    companion object {
        fun start(activity: Activity, view: View, articleDm: ArticleDM?) {
            val extras = Bundle()
            extras.putParcelable("dataModel", articleDm)
            activity.startActivity(view, ArticleDetailsActivity::class.java, extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configViewModel()
    }


    override fun setupViews() {
        binding = ActivityArticleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        initToolbar()
    }

    private fun configViewModel() {
        viewModel.readExtras(intent.extras)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }

    override fun getInjectViewModel()=ViewModelProvider(this).get(ArticleDetailsViewModel::class.java)
}