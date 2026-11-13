package com.arrazyfathan.nytimes.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.utils.Utils
import com.arrazyfathan.nytimes.designsystem.theme.DomineBold
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansRegular
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansSemiMedium

/**
 * Created by Ar Razy Fathan Rabbani on 15/06/23.
 */

@Composable
fun NewsItemArticle(
    article: Article,
    modifier: Modifier = Modifier,
    onItemSelected: (Article) -> Unit,
) {
    val context = LocalContext.current
    val timeAgo =
        remember(article.publishedDate) {
            if (article.publishedDate.isBlank()) "" else Utils.dateTimeAgo(article.publishedDate)
        }
    val sectionText =
        remember(article.subsection, article.section) {
            article.subsection.ifEmpty { article.section }.replaceFirstChar { it.uppercase() }
        }
    val imageUrl = remember(article.multimedia) { article.getImage() }
    val imageRequest =
        remember(imageUrl, context) {
            ImageRequest
                .Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build()
        }
    val textGray = colorResource(id = R.color.text_gray)

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onItemSelected(article) }
                .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                ChipText(text = sectionText)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color.Black,
                    fontFamily = DomineBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style =
                        TextStyle(
                            platformStyle = PlatformTextStyle(includeFontPadding = false),
                        ),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.description,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color.Black,
                    fontFamily = NotoSansRegular,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style =
                        TextStyle(
                            platformStyle = PlatformTextStyle(includeFontPadding = false),
                        ),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(130.dp)
                        .clip(RoundedCornerShape(12.dp)),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = article.byline,
                fontSize = 10.sp,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                maxLines = 1,
                color = textGray,
                fontFamily = NotoSansSemiMedium,
                modifier = Modifier.weight(1f, fill = false),
            )
            Text(
                text = " • ",
                fontSize = 10.sp,
                color = textGray,
                fontFamily = NotoSansSemiMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            Text(
                text = timeAgo,
                fontSize = 10.sp,
                color = textGray,
                fontFamily = NotoSansSemiMedium,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsItemArticlePreview() {
    NewsItemArticle(
        Article(
            articleId = "1",
            description =
                "Nearly every Mac app has at least one action that takes one click " +
                    "(or three clicks) too many to execute.",
            byline = "By Michael S. Schmidt and Charlie Savage",
            createdDate = "1 Month Ago",
            itemType = "Article",
            kicker = "Kicker",
            materialTypeFacet = "",
            multimedia = emptyList(),
            publishedDate = "",
            section = "",
            shortUrl = "",
            subsection = "",
            title =
                "Judge Aileen M. Cannon, under scrutiny for past rulings favoring " +
                    "the former president, has presided over only a few criminal cases.",
            updatedDate = "",
            uri = "",
            url = "",
            isBookmarked = false,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {}
}
