package com.srnapit.newsapps.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.srnapit.newsapps.network.Article
import com.srnapit.newsapps.R

@Composable
fun NewsItemCard(article: Article) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(bottom = 10.dp)) {
            val imageUrl = article.urlToImage
            val painter = if (!imageUrl.isNullOrEmpty()) {
                rememberAsyncImagePainter(model = imageUrl)
            } else {
                painterResource(id = R.drawable.img_not_found)
            }

            Image(
                painter = painter,
                contentDescription = article.title ?: "Article image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = article.title ?: "(No title)",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
            )

            Text(
                text = article.description ?: "(No description)",
                style = MaterialTheme.typography.bodyMedium,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
            )
        }
    }
}
