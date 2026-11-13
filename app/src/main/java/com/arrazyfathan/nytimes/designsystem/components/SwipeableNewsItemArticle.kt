package com.arrazyfathan.nytimes.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
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
    val remove =
        SwipeAction(
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

    SwipeableActionsBox(
        swipeThreshold = 200.dp,
        endActions = listOf(remove),
    ) {
        NewsItemArticle(
            article = article,
            modifier = modifier,
            onItemSelected = onItemSelected,
        )
    }
}
