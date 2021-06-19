package com.tempo.news.data.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder()
            .header("Cache-Control", "no-store")
            .build()
    }
}