package com.arrazyfathan.nytimes.presentation.article

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.designsystem.components.ChipText
import com.arrazyfathan.nytimes.designsystem.components.bounceClick
import com.arrazyfathan.nytimes.designsystem.components.pressClickEffect
import com.arrazyfathan.nytimes.designsystem.theme.DomineRegular
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansRegular
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansSemiBold
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansSemiMedium
import com.arrazyfathan.nytimes.utils.convertDateToPattern

/**
 * Created by Ar Razy Fathan Rabbani on 18/06/23.
 */

@Composable
fun ArticleDetailScreen(
    viewModel: ArticleDetailViewModel,
    article: Article,
    onShareClicked: () -> Unit,
    openLink: () -> Unit,
    modifier: Modifier,
) {
    val bool by viewModel.checkArticleIsBookmarked(article.shortUrl).collectAsState(initial = false)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    val cornerRadius by animateDpAsState(targetValue = if (isPressed.value) 10.dp else 50.dp)
    val formattedDate =
        convertDateToPattern(inputDate = article.publishedDate, outputFormat = "EEEE, MMMM dd yyyy")

    Scaffold { paddingValues ->
        Column(
            modifier = modifier.verticalScroll(rememberScrollState()).padding(paddingValues),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ChipText(
                    text = article.section.ifBlank { "News" }
                        .replaceFirstChar { it.uppercase() },
                    fontSize = 12.sp,
                )
                IconButton(
                    modifier = Modifier
                        .pressClickEffect(),
                    onClick = onShareClicked,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                    )
                }
            }

            Box(
                modifier = Modifier
                    .bounceClick()
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

                IconButton(
                    onClick = {
                        if (bool) {
                            viewModel.removeBookmark(
                                article.copy(
                                    articleId = article.shortUrl,
                                    isBookmarked = true,
                                ),
                            )
                        } else {
                            viewModel.bookmarkArticle(
                                article.copy(
                                    articleId = article.shortUrl,
                                    isBookmarked = true,
                                ),
                            )
                        }
                    },
                    interactionSource = interactionSource,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(cornerRadius))
                        .background(Color.White)
                        .size(54.dp),
                ) {
                    Icon(
                        painter = if (bool) {
                            painterResource(id = R.drawable.ic_bookmark_fill)
                        } else {
                            painterResource(
                                R.drawable.ic_bookmark_line,
                            )
                        },
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(24.dp),
                    )
                }
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
                color = Color.Black.copy(alpha = 0.3f),
                thickness = 0.5.dp,
            )

            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                ) {
                    Text(
                        text = "Author :",
                        fontSize = 12.sp,
                        fontFamily = NotoSansRegular,

                    )
                    Text(
                        text = article.byline,
                        fontSize = 14.sp,
                        fontFamily = NotoSansSemiBold,
                    )
                }

                Column(
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                ) {
                    Text(
                        text = "Date :",
                        fontSize = 12.sp,
                        fontFamily = NotoSansRegular,
                    )
                    Text(
                        text = formattedDate,
                        fontSize = 14.sp,
                        fontFamily = NotoSansSemiBold,
                    )
                }
            }

            Divider(
                modifier = Modifier.padding(16.dp),
                color = Color.Black.copy(alpha = 0.3f),
                thickness = 0.5.dp,
            )

            Text(
                text = article.description,
                fontSize = 13.sp,
                fontFamily = NotoSansSemiMedium,
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
            )

            Card(
                elevation = 0.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color.White,
                border = BorderStroke(0.5.dp, Color.Black.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .bounceClick()
                    .clickable { openLink() },
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(10.dp),
                ) {
                    val (image, content, icon) = createRefs()

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(article.getImage())
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .constrainAs(image) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                    )

                    Column(
                        modifier = Modifier
                            .constrainAs(content) {
                                top.linkTo(image.top)
                                bottom.linkTo(image.bottom)
                                start.linkTo(image.end, 8.dp)
                                end.linkTo(icon.start, 16.dp)
                                width = Dimension.fillToConstraints
                            },
                    ) {
                        Text(
                            text = "OPEN LINK",
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontFamily = NotoSansSemiBold,
                        )
                        Text(
                            text = article.title,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 11.sp,
                            fontFamily = NotoSansRegular,
                        )
                    }

                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .constrainAs(icon) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                                height = Dimension.preferredWrapContent
                            },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_exsternal_link),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(20.dp),
                        )
                    }
                }
            }
        }
    }
}
