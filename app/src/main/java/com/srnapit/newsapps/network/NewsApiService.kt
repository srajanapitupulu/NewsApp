package com.srnapit.newsapps.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us"
    ): NewsResponse

    @GET("everything")
    suspend fun getNewsList(
        @Query("q") query: String = ""
    ): NewsResponse
}