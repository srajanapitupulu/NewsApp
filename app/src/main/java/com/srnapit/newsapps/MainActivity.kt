package com.srnapit.newsapps

import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsFeedApp()
        }
    }
}

@Composable
fun NewsFeedApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold { innerPadding ->
                Text(
                    text = "Hello NewsFeed 👋",
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsFeedApp() {
    NewsFeedApp()
}