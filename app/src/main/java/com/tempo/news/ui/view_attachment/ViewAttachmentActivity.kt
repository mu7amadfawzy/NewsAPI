package com.tempo.news.ui.view_attachment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.tempo.news.databinding.ViewAttachmentActivityBinding
import com.tempo.news.utils.Extensions.startActivity

/**
 * Created by Fawzy on 24,March,2019.
 * ma7madfawzy@gmail.com
 */
class ViewAttachmentActivity : AppCompatActivity() {
    private var filePath: String? = null
    private lateinit var binding: ViewAttachmentActivityBinding
    private lateinit var viewModel: ViewAttachmentViewModel

    companion object {
        fun start(context: Activity, filePath: String, sharedElement: View) {
            val extras = Bundle()
            extras.putString("filePath", filePath)
            context.startActivity(sharedElement, ViewAttachmentActivity::class.java, extras)
        }

        fun start(context: Activity, filePath: String?) {
            val extras = Bundle()
            extras.putString("filePath", filePath)
            context.startActivity(ViewAttachmentActivity::class.java, extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        configViewModel()
    }

    private fun bindView() {
        binding = ViewAttachmentActivityBinding.inflate(layoutInflater)
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this).get(ViewAttachmentViewModel::class.java)
        viewModel.readExtras(intent.extras)
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }

}