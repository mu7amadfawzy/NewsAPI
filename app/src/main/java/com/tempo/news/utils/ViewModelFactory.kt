package com.tempo.news.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tempo.news.data.api.RetrofitBuilder
import com.tempo.news.data.data_sources.NewsDataSource
import com.tempo.news.data.repositories.NewsRepository
import com.tempo.news.ui.home.HomeActivityViewModel

/**
 * ViewModel provider factory to instantiate AddContactViewModel.
 * Required given AddContactViewModel has a non-empty constructor
 */
class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java)) {
            return HomeActivityViewModel(NewsRepository(NewsDataSource(RetrofitBuilder.apiService))) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}