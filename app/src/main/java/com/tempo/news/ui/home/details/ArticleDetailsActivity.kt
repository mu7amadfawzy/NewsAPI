package com.tempo.news.ui.home.details

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.tempo.news.data.model.ArticleDM
import com.tempo.news.databinding.ActivityArticleDetailsBinding
import com.tempo.news.ui.view_attachment.ViewAttachmentActivity
import com.tempo.news.utils.Extensions.startActivity


class ArticleDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleDetailsViewModel
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
        initViews()
    }


    private fun initViews() {
        binding = ActivityArticleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        initToolbar()
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this).get(ArticleDetailsViewModel::class.java)
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

    fun onNewsImageCLicked(view: View) {
        ViewAttachmentActivity.start(this, view.tag.toString(), view)
    }
}