package com.srnapit.newsapps.data.network

import com.srnapit.newsapps.data.model.NewsResponse
import retrofit2.http.GET
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