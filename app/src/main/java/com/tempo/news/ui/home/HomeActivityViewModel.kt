package com.tempo.news.ui.home

import androidx.lifecycle.MutableLiveData
import com.tempo.news.data.model.ArticleDM
import com.tempo.news.data.model.ResponseDM
import com.tempo.news.data.model.Result
import com.tempo.news.data.repositories.ArticlesRepository
import com.tempo.news.ui.base.BaseLoaderAdapter
import com.tempo.news.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeActivityViewModel @Inject constructor(val repository: ArticlesRepository) :
    BaseViewModel(),
    BaseLoaderAdapter.PaginationHandler {
    val model = HomeActivityModel()

    val newsResult = MutableLiveData<List<ArticleDM>>()

    private fun searchRepo(queryString: String, page: Int = 1) {
        // can be launched in a separate asynchronous job
        model.loading = page == 1
        coroutineScope.launch(Dispatchers.IO) {
            onDataLoaded(repository.fetchArticles(queryString, page))
        }
    }

    private fun onDataLoaded(result: Result<ResponseDM<List<ArticleDM>>>) {
        when (result) {
            is Result.Success<ResponseDM<List<ArticleDM>>> -> onSuccess(result)
            is Result.Error -> onError(result)
        }
        model.loading = false
    }

    private fun onError(result: Result.Error) {
        //snack the error if theres is data already so the recycler is taking the whole screen
        model.showToast = newsResult.value!=null||newsResult.value?.isNotEmpty()==true
        model.messageText = result.exception
//        newsResult.value=(emptyList())
    }

    private fun onSuccess(result: Result.Success<ResponseDM<List<ArticleDM>>>) {
        result.data.articles.let { newsResult.postValue(it) }
        model.onSuccess()
    }

    override fun onLoadMore(page: Int, totalRows: Int) {
        loadData(page)
    }

    fun loadData(page: Int = 1) {
        searchRepo(model.queryText.toString(), page)
    }

    fun onQueryChanged(query: String) {
        model.queryText = query
        loadData(1)
    }
}