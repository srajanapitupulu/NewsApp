package com.srnapit.newsapps.data.repository

import com.srnapit.newsapps.data.network.NewsApiService
import javax.inject.Inject

class NewsRepository@Inject constructor(
    private val api: NewsApiService
) {
    suspend fun fetchTopHeadlines() = api.getTopHeadlines()
}