package com.srnapit.newsapps.ui

import android.widget.GridView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.srnapit.newsapps.network.Article
import com.srnapit.newsapps.ui.viewmodel.NewsUIState
import com.srnapit.newsapps.ui.viewmodel.NewsViewModel

@Composable
fun NewsFeedScreen(viewModel: NewsViewModel, isGridView: Boolean, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()

    when(uiState) {
        is NewsUIState.Loading -> {
            LoadingView()
        }
        is NewsUIState.Success -> {

            if (isGridView) {
                NewsGrid((uiState as NewsUIState.Success).news, modifier)
            } else {
                NewsList((uiState as NewsUIState.Success).news, modifier)
            }


        }
        is NewsUIState.Error -> {
            ErrorView((uiState as NewsUIState.Error).message)
        }

    }
}

@Composable
fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Error: $message")
    }
}

@Composable
fun NewsList(articles: List<Article>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(articles) { article ->
            NewsItemCard(article)
        }
    }
}

@Composable
fun NewsGrid(articles: List<Article>, modifier: Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(articles) { article ->
            NewsItemCard(article)
        }
    }
}