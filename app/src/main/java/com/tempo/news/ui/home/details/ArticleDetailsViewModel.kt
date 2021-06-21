package com.tempo.news.ui.home.details

import android.os.Bundle
import com.tempo.news.data.model.ArticleDM
import com.tempo.news.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleDetailsViewModel @Inject constructor() : BaseViewModel() {

    val model = ArticleDetailsModel()

    fun readExtras(extras: Bundle?) {
        coroutineScope.launch {
            model.loading = true
            model.dataModel = fetchExtrasAsync(extras)
            model.loading = false
        }
    }

    private suspend fun fetchExtrasAsync(extras: Bundle?): ArticleDM? {
        return withContext(Dispatchers.IO) { extras?.getParcelable("dataModel") }
    }
}