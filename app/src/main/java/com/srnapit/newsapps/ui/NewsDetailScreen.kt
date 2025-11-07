package com.srnapit.newsapps.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.srnapit.newsapps.R
import com.srnapit.newsapps.network.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(article: Article, navController: NavController, innerPadding: PaddingValues) {

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        val painter = if (!article.urlToImage.isNullOrEmpty()) {
            rememberAsyncImagePainter(model = article.urlToImage)
        } else {
            painterResource(id = R.drawable.img_not_found)
        }

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.title ?: "Untitled",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = article.description ?: "No description available.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}