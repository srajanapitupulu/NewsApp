package com.srnapit.newsapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.srnapit.newsapps.ui.NewsFeedScreen
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

            var isGridView by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                viewModel.loadNewsHeadlines()
            }
            Scaffold (
                topBar = {
                    TopAppBar(
                        title = { Text("News App") },
                        actions = {
                            IconButton(onClick = { isGridView = !isGridView }) {
                                Icon(
                                    painter = if (isGridView) painterResource(id = R.drawable.grid_view) else painterResource(id = R.drawable.list_view),
                                    contentDescription = if (isGridView) "List View" else "Grid View"
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                NewsFeedScreen(viewModel = viewModel, isGridView, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsFeedApp() {
    NewsFeedApp()
}