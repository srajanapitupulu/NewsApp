package com.srnapit.newsapps.network

import java.util.Date

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?,
    val author: String?,
    val publishedAt: String?,
    val content: String?
)
