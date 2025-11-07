package com.srnapit.newsapps.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.srnapit.newsapps.data.model.Article
import com.srnapit.newsapps.R

@Composable
fun NewsItemCard(article: Article, onSelected: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onSelected(article) }
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
                colorFilter = if (imageUrl.isNullOrEmpty()) ColorFilter.tint(Color.White) else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = if (imageUrl.isNullOrEmpty()) ContentScale.Inside else ContentScale.Crop
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
