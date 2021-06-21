package com.tempo.news.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.tempo.news.BR
import com.tempo.news.di.modules.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel, B : ViewDataBinding> : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM
    lateinit var binding: B
    private val job = Job()
    protected val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        inflateBinding()
        setupViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModelClass())
    }

    private fun inflateBinding() {
        binding = getViewBinding()
        setContentView(binding.root)
        binding.setVariable(BR.viewModel, viewModel)
    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.coroutineContext.cancelChildren()
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun getViewModelClass(): Class<VM>
    abstract fun getViewBinding(): B
    abstract fun setupViews()
}