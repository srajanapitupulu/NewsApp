package com.srnapit.newsapps.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import com.srnapit.newsapps.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUIState>(NewsUIState.Loading)
    val uiState: StateFlow<NewsUIState> = _uiState


    fun loadNewsHeadlines() {
        viewModelScope.launch {
            _uiState.value = NewsUIState.Loading

            try {
                val response = newsRepository.fetchTopHeadlines()

                if (response.status == "ok") {
                    _uiState.value = NewsUIState.Success(response.articles)
                } else {
                    _uiState.value = NewsUIState.Error("An error occurred: ${response.status}")
                }
            }
            catch (e: Exception) {
                _uiState.value = NewsUIState.Error(e.message ?: "Unknown error")
            }
        }
    }
}