package com.tempo.news.data.data_sources

import com.tempo.news.data.api.ApiService
import com.tempo.news.data.api.RetrofitBuilder
import com.tempo.news.data.model.ArticleDM
import com.tempo.news.data.model.ResponseDM
import com.tempo.news.data.model.Result
import retrofit2.HttpException

/**
 * DataSource class that works with local and remote data sources.
 */
class NewsDataSource(private val apiService: ApiService) {
    /**
     * Search  whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    suspend fun fetchArticles(
        query: String, sortBy: String, source: String,
        page: Int
    ): Result<ResponseDM<List<ArticleDM>>> {
        return requestData(query, sortBy, source,page)
    }

    private suspend fun requestData(query: String, sortBy: String, source: String, page: Int)
            : Result<ResponseDM<List<ArticleDM>>> {
        return try {
            when (val result = getResultCall(query, sortBy, source,page)) {
                is Result.Success<ResponseDM<List<ArticleDM>>> -> {
                    Result.Success(result.data)
                }
                is Result.Error -> {
                    Result.Error(result.exception)
                }
            }
        } catch (exception: HttpException) {
            Result.Error(exception.message.toString())
        }
    }

    private suspend fun getResultCall(
        query: String, sortBy: String, source: String, page: Int
    ): Result<ResponseDM<List<ArticleDM>>> {
        return RetrofitBuilder.safeApiCall(call = {
            apiService.fetchNews(
                API_KEY, query, page , NETWORK_PAGE_SIZE, sortBy, source
            )
        })
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
        private const val API_KEY = "397c2d4197f64105b302f4fd74bbd488"
    }
}