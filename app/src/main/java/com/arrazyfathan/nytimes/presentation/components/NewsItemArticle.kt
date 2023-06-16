package com.arrazyfathan.nytimes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.preferredWrapContent
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.presentation.theme.Domine
import com.arrazyfathan.nytimes.presentation.theme.NotoSans

/**
 * Created by Ar Razy Fathan Rabbani on 15/06/23.
 */

@Composable
fun NewsItemArticle(
    article: Article,
    modifier: Modifier = Modifier,
    onItemSelected: (Article) -> Unit,
) {
    ConstraintLayout(
        modifier = modifier.clickable { onItemSelected(article) },
    ) {
        val (title, description, image, chips, author, published) = createRefs()

        Text(
            text = article.itemType,
            fontSize = 10.sp,
            color = Color.Black,
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(colorResource(id = R.color.gray_chip))
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .constrainAs(chips) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                },
        )

        Text(
            text = article.title,
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = Domine,
            maxLines = 2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(chips.start)
                    top.linkTo(chips.bottom, 8.dp)
                    end.linkTo(image.start, 16.dp)

                    height = preferredWrapContent
                    width = preferredWrapContent
                },
        )

        Text(
            text = article.description,
            fontSize = 12.sp,
            color = Color.Black,
            fontFamily = NotoSans,
            maxLines = 3,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .constrainAs(description) {
                    start.linkTo(chips.start)
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(image.start, 16.dp)
                    height = preferredWrapContent
                    width = preferredWrapContent
                },
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.getImage())
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(130.dp)
                .clip(RoundedCornerShape(12.dp))
                .constrainAs(image) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsItemArticlePreview() {
    NewsItemArticle(
        Article(
            articleId = "1",
            description = "Nearly every Mac app has at least one action that takes one click (or three clicks) too many to execute—nobody wants to go through four different menus or use the search bar to find the action you need, especially if it’s something you use often. When you dis…",
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
            title = "Judge Aileen M. Cannon, under scrutiny for past rulings favoring the former president, has presided over only a few criminal cases that went to trial.",
            updatedDate = "",
            uri = "",
            url = "",
            isBookmarked = false,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
    }
}
