package com.tempo.news.data.api

import android.util.Log
import com.tempo.news.data.model.Result
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private const val BASE_URL = "https://newsapi.org/v2/"

    lateinit var apiService: ApiService

    init {
        makeService()
    }

    private fun makeService() {
        val retrofit: Retrofit = Retrofit.Builder()
            .client(okHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.apiService = retrofit.create(ApiService::class.java)
    }

    private fun okHttpClient() = OkHttpClient()
        .newBuilder().callTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(getInterceptor()).build()


    private fun getInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    // usage sample safeApiCall(call = { apiService.fetchNews(API_KEY) })
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val myResp = call.invoke()
            if (myResp.isSuccessful) {
                Result.Success(myResp.body()!!)
            } else {
                if (myResp.code() == 403) {
                    Log.i("responseCode", "Authentication failed")
                }

                Result.Error(myResp.errorBody()?.string() ?: "Something goes wrong")
            }

        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }

}