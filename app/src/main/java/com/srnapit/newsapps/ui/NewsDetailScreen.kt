package com.srnapit.newsapps.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.srnapit.newsapps.R
import com.srnapit.newsapps.network.Article
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(article: Article, innerPadding: PaddingValues) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

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
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = article.publishedAt?.let {
                try {
                    val zonedDateTime = ZonedDateTime.parse(it)
                    val dayOfWeek = zonedDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

                    val dayOfMonth = zonedDateTime.dayOfMonth
                    val suffix = when (dayOfMonth) {
                        1, 21, 31 -> "st"
                        2, 22 -> "nd"
                        3, 23 -> "rd"
                        else -> "th"
                    }
                    val dayWithSuffix = "$dayOfMonth$suffix"

                    // Format the rest of the date/time
                    val monthYearTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy hh:mma", Locale.ENGLISH)
                    val formattedMonthYearTime = zonedDateTime.format(monthYearTimeFormatter)

                    "Published at: $dayOfWeek, $dayWithSuffix $formattedMonthYearTime"
                } catch (e: Exception) {
                    "Published at: -"
                }
            } ?: "Published at: -",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Author: ${ article.author ?: "-" }" ,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            )
        ) {
            Text(
                text = article.description ?: "No description available.",
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = article.content?.replace(Regex("<.*?>"), "")?.substringBeforeLast("[")
                ?: "No content available.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp),

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Read more",
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.purple_500),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable {
                    article.url?.let { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    } ?: run {
                        showDialog = true
                    }
                }
        )

        Spacer(modifier = Modifier.height(32.dp))
    }

}