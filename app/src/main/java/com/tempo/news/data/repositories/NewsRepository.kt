package com.tempo.news.data.repositories

import com.tempo.news.data.data_sources.NewsDataSource

/**
 * Class that requests fetchArticles on the remote data source.
 */

class NewsRepository(val dataSource: NewsDataSource) {

    suspend fun fetchArticles(
        q: String = "",
        page:Int,
        sortBy: String = "popularity",
        source: String = "bbc-news"
    ) = dataSource.fetchArticles(q, sortBy, source,page)
}