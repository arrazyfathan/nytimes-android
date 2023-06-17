package com.arrazyfathan.nytimes.presentation.bookmark

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.designsystem.components.NewsItemArticle
import com.arrazyfathan.nytimes.designsystem.theme.DomineBold
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansRegular
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansSemiBold

/**
 * Created by Ar Razy Fathan Rabbani on 15/06/23.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel,
    modifier: Modifier = Modifier,
    onClicked: (Article) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberLazyListState()
    var showDialog by remember { mutableStateOf(false) }

    val firstItemVisible by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset == 0
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = if (firstItemVisible) 0.dp else 4.dp,
            ) {
                Text(
                    text = "Bookmark",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontFamily = DomineBold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                )
            }
        },
    ) { paddingValues ->

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        "Are you sure you want to delete this?",
                        fontFamily = NotoSansSemiBold,
                        fontSize = 18.sp,
                    )
                },
                text = {
                    Text(
                        "This action cannot be undone",
                        fontFamily = NotoSansRegular,
                        fontSize = 16.sp,
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        state.selectedArticle?.let(viewModel::removeArticle)
                        showDialog = false
                    }) {
                        Text("Delete", fontFamily = NotoSansSemiBold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel", fontFamily = NotoSansSemiBold)
                    }
                },
            )
        }

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (state.articles.isEmpty()) {
                Text(
                    text = "No articles in Bookmark 🙅🏻‍",
                    fontFamily = NotoSansRegular,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            } else {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    state = scrollState,
                ) {
                    items(state.articles, key = { it.articleId }) { article ->
                        NewsItemArticle(
                            article = article,
                            onItemSelected = { onClicked(article) },
                            onSwipeLeft = {
                                viewModel.selectArticle(it)
                                showDialog = !showDialog
                            },
                            modifier = Modifier
                                .animateItemPlacement()
                                .fillParentMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}
