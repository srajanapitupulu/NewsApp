package com.srnapit.newsapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.srnapit.newsapps.ui.ErrorView
import com.srnapit.newsapps.ui.LoadingView
import com.srnapit.newsapps.ui.NewsDetailScreen
import com.srnapit.newsapps.ui.NewsGrid
import com.srnapit.newsapps.ui.NewsList
import com.srnapit.newsapps.ui.viewmodel.NewsUIState
import com.srnapit.newsapps.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsFeedApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val viewModel: NewsViewModel = hiltViewModel()
            val navController = rememberNavController()
            var isGridView by remember { mutableStateOf(false) }
            val uiState by viewModel.uiState.collectAsState()

            val navControllerBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navControllerBackStackEntry?.destination?.route

            LaunchedEffect(Unit) {
                viewModel.loadNewsHeadlines()
            }

            Scaffold (
                topBar = {
                    TopAppBar(
                        title = { Text("News App") },
                        navigationIcon = {
                            if (currentDestination == "details") {
                                IconButton(onClick = { navController.navigateUp() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        },
                        actions = {
                            if (currentDestination == "news_feed") {
                                IconButton(onClick = { isGridView = !isGridView }) {
                                    Icon(
                                        painter = if (isGridView) painterResource(id = R.drawable.grid_view) else painterResource(
                                            id = R.drawable.list_view
                                        ),
                                        contentDescription = if (isGridView) "List View" else "Grid View"
                                    )
                                }
                            }
                        }
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "news_feed",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("news_feed") {
                        when(uiState) {
                            is NewsUIState.Loading -> {
                                LoadingView()
                            }
                            is NewsUIState.Success -> {
                                if (isGridView) {
                                    NewsGrid(
                                        (uiState as NewsUIState.Success).news,
                                        Modifier.padding(innerPadding)
                                    ) { selectedArticle ->
                                        navController.navigate("details")
                                        viewModel.setSelectedArticle(selectedArticle)
                                    }
                                } else {
                                    NewsList(
                                        (uiState as NewsUIState.Success).news,
                                        Modifier.padding(innerPadding)
                                    ) { selectedArticle ->
                                        navController.navigate("details")
                                        viewModel.setSelectedArticle(selectedArticle)
                                    }
                                }
                            }
                            is NewsUIState.Error -> {
                                ErrorView((uiState as NewsUIState.Error).message)
                            }

                        }
                    }

                    composable("details") {
                        val selected = viewModel.selectedArticle
                        selected?.let {
                            NewsDetailScreen(it, innerPadding)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsFeedApp() {
    NewsFeedApp()
}