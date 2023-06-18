package com.arrazyfathan.nytimes.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.constraintlayout.compose.Dimension.Companion.preferredWrapContent
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.utils.Utils
import com.arrazyfathan.nytimes.designsystem.theme.DomineBold
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansRegular
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansSemiMedium
import com.arrazyfathan.nytimes.designsystem.theme.colorWarning
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

/**
 * Created by Ar Razy Fathan Rabbani on 15/06/23.
 */
@Composable
fun SwipeableNewsItemArticle(
    article: Article,
    modifier: Modifier = Modifier,
    onSwipeLeft: (Article) -> Unit,
    onItemSelected: (Article) -> Unit,
) {
    val remove = SwipeAction(
        onSwipe = { onSwipeLeft(article) },
        background = colorWarning,
        icon = {
            Icon(
                painterResource(
                    id = R.drawable.ic_round_delete,
                ),
                modifier = Modifier.padding(16.dp),
                contentDescription = null,
                tint = Color.White,
            )
        },
    )

    val timeAgo = article.publishedDate.let { Utils.dateTimeAgo(it) }
    val sectionText = article.subsection.ifEmpty { article.section }

    SwipeableActionsBox(
        swipeThreshold = 200.dp,
        endActions = listOf(remove),
    ) {
        ConstraintLayout(
            modifier = modifier.clickable { onItemSelected(article) },
        ) {
            val (title, description, image, chips, author) = createRefs()

            ChipText(
                text = sectionText.replaceFirstChar { it.uppercase() },
                modifier = Modifier
                    .constrainAs(chips) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                    },
            )

            Text(
                text = article.title,
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = DomineBold,
                maxLines = 2,
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
                fontFamily = NotoSansRegular,
                maxLines = 3,
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
                    },
            )

            Row(
                modifier = Modifier
                    .constrainAs(author) {
                        top.linkTo(description.bottom, 16.dp)
                        start.linkTo(chips.start)
                        end.linkTo(parent.end, 16.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                        width = fillToConstraints
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "${article.byline} • ",
                    fontSize = 10.sp,
                    textAlign = TextAlign.Start,
                    color = colorResource(id = R.color.text_gray),
                    fontFamily = NotoSansSemiMedium,
                )

                Text(
                    text = timeAgo,
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.text_gray),
                    fontFamily = NotoSansSemiMedium,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}
