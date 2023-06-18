package com.arrazyfathan.nytimes.presentation.article

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.designsystem.components.ChipText
import com.arrazyfathan.nytimes.designsystem.theme.DomineRegular
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansRegular

/**
 * Created by Ar Razy Fathan Rabbani on 18/06/23.
 */

@Composable
fun ArticleDetailScreen(
    viewModel: ArticleDetailViewModel,
    article: Article,
    modifier: Modifier,
) {
    val bool by viewModel.checkArticleIsBookmarked(article.shortUrl).collectAsState(initial = false)

    Log.d("wlwl", "state : $bool articleId : ${article.shortUrl}")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (bool) {
                    viewModel.removeBookmark(article)
                } else {
                    viewModel.bookmarkArticle(
                        article.copy(
                            articleId = article.shortUrl,
                            isBookmarked = true,
                        ),
                    )
                }
            }) {
                Icon(
                    painter = if (bool) {
                        painterResource(id = R.drawable.bookmark_fill)
                    } else {
                        painterResource(
                            R.drawable.bookmark,
                        )
                    },
                    contentDescription = null,
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = modifier.verticalScroll(rememberScrollState()).padding(paddingValues),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ChipText(text = article.section.ifBlank { "News" }, fontSize = 12.sp)
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(250.dp),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(article.getMainImage())
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                )

                Text(
                    text = article.getImageCopyright(),
                    fontSize = 10.sp,
                    fontFamily = NotoSansRegular,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .alpha(0.5f)
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp),
                )
            }

            Text(
                text = article.getImageCaption(),
                fontSize = 10.sp,
                fontFamily = NotoSansRegular,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            )

            Text(
                text = article.title,
                fontSize = 24.sp,
                fontFamily = DomineRegular,
                color = Color.Black,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            )

            Divider(
                modifier = Modifier.padding(16.dp),
                color = Color.Black,
                thickness = 0.5.dp,
            )

            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Author :",
                    )
                    Text(
                        text = article.byline,
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Date :",
                    )
                    Text(
                        text = article.publishedDate,
                    )
                }
            }

            Divider(
                modifier = Modifier.padding(16.dp),
                color = Color.Black,
                thickness = 0.5.dp,
            )

            Text(
                text = article.description,
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
            )

            Card(
                elevation = 0.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color.White,
                border = BorderStroke(0.5.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp),
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(article.getImage())
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(4.dp)),
                    )
                }
            }
        }
    }
}
