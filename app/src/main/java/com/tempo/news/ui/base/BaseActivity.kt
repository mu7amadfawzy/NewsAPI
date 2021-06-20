package com.tempo.news.ui.base

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

abstract class BaseActivity<T : BaseViewModel> : DaggerAppCompatActivity() {

    lateinit var viewModel: T
    val job = Job()
    val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    abstract fun getInjectViewModel(): T

    abstract fun setupViews()

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getInjectViewModel()
        setupViews()
    }
}