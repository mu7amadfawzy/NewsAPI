package com.tempo.news.ui.home.details

import android.os.Bundle
import com.tempo.news.data.model.ArticleDM
import com.tempo.news.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleDetailsViewModel @Inject constructor() : BaseViewModel() {

    val model = ArticleDetailsModel()

    fun readExtras(extras: Bundle?) {
        coroutineScope.launch(Dispatchers.IO) {
            model.loading = true
            model.dataModel = fetchExtras(extras)
            model.loading = false
        }
    }

    private suspend fun fetchExtras(extras: Bundle?): ArticleDM =
        extras?.getParcelable("dataModel")!!

}