package com.tempo.news.data.api

import com.tempo.news.data.model.ArticleDM
import com.tempo.news.data.model.ResponseDM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("Everything")
    suspend fun
            fetchNews(
        @Query("apiKey") api_key: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sortBy") sortBy: String,
        @Query("sources") source: String
    ): Response<ResponseDM<List<ArticleDM>>>
}