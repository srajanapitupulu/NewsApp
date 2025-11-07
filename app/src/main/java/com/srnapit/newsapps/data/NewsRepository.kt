package com.srnapit.newsapps.data

import com.srnapit.newsapps.network.NewsApiService
import javax.inject.Inject

class NewsRepository@Inject constructor(
    private val api: NewsApiService
) {
    suspend fun fetchTopHeadlines(apiKey: String) = api.getTopHeadlines(apiKey = apiKey)
}