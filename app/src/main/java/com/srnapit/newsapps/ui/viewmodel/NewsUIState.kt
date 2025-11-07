package com.srnapit.newsapps.ui.viewmodel

import com.srnapit.newsapps.network.Article

sealed interface NewsUIState {
    object Loading : NewsUIState
    data class Success(val news: List<Article>) : NewsUIState
    data class Error(val message: String) : NewsUIState
}