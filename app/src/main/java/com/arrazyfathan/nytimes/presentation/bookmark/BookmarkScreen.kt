package com.arrazyfathan.nytimes.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.presentation.components.NewsItemArticle
import com.arrazyfathan.nytimes.presentation.theme.Domine

/**
 * Created by Ar Razy Fathan Rabbani on 15/06/23.
 */

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel,
    modifier: Modifier = Modifier,
    onClicked: (Article) -> Unit,
) {
    val articles by viewModel.getAllBookmarkedArticle().observeAsState()

    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Bookmark",
            color = Color.Black,
            fontSize = 24.sp,
            fontFamily = Domine,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
        )

        articles?.let { articles ->
            LazyColumn(
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                items(articles) { article ->
                    NewsItemArticle(
                        article = article,
                        onItemSelected = { onClicked(article) },
                        modifier = Modifier.fillParentMaxWidth(),
                    )
                }
            }
        }
    }
}
