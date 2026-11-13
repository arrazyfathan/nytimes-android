package com.arrazyfathan.nytimes.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrazyfathan.nytimes.core.domain.AppError
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansRegular
import com.arrazyfathan.nytimes.designsystem.theme.NotoSansSemiBold
import com.arrazyfathan.nytimes.presentation.topstories.TopStoriesUiState
import com.arrazyfathan.nytimes.presentation.topstories.sections
import kotlinx.coroutines.launch

/**
 * Created by Ar Razy Fathan Rabbani on 19/06/23.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    sectionStates: Map<String, TopStoriesUiState>,
    onItemSelected: (Article) -> Unit,
    onRetry: (String) -> Unit = {},
) {
    Column(modifier = modifier.background(Color.White)) {
        Tabs(pagerState = pagerState)
        Spacer(modifier = Modifier.height(8.dp))
        TabsContent(
            pagerState = pagerState,
            sectionStates = sectionStates,
            onItemSelected = onItemSelected,
            onRetry = onRetry,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    val tabListState = rememberLazyListState()

    LaunchedEffect(pagerState.currentPage) {
        val layoutInfo = tabListState.layoutInfo
        val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == pagerState.currentPage }
        if (itemInfo == null) {
            tabListState.animateScrollToItem(pagerState.currentPage)
        } else {
            val itemStart = itemInfo.offset
            val itemEnd = itemInfo.offset + itemInfo.size
            val viewportStart = layoutInfo.viewportStartOffset
            val viewportEnd = layoutInfo.viewportEndOffset
            if (itemStart < viewportStart) {
                tabListState.animateScrollBy((itemStart - viewportStart).toFloat())
            } else if (itemEnd > viewportEnd) {
                tabListState.animateScrollBy((itemEnd - viewportEnd).toFloat())
            }
        }
    }

    LazyRow(
        state = tabListState,
        modifier =
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(sections) { index, title ->
            val isSelected = pagerState.currentPage == index
            Box(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (isSelected) Color.Black else Color.Transparent)
                        .clickable { scope.launch { pagerState.animateScrollToPage(index) } }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = title,
                    color = if (isSelected) Color.White else Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = NotoSansSemiBold,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(
    pagerState: PagerState,
    sectionStates: Map<String, TopStoriesUiState>,
    onItemSelected: (Article) -> Unit,
    onRetry: (String) -> Unit = {},
) {
    HorizontalPager(
        state = pagerState,
        key = { page -> sections[page] },
    ) { page ->
        val sectionKey = sections[page].lowercase()
        val state = sectionStates[sectionKey] ?: TopStoriesUiState.Loading
        val listState =
            rememberSaveable(sectionKey, saver = LazyListState.Saver) {
                LazyListState()
            }

        ContentSection(
            state = state,
            listState = listState,
            onItemSelected = onItemSelected,
            onRetry = { onRetry(sectionKey) },
        )
    }
}

@Composable
fun ContentSection(
    state: TopStoriesUiState,
    listState: LazyListState,
    onItemSelected: (Article) -> Unit,
    onRetry: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
    ) {
        when (state) {
            TopStoriesUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            is TopStoriesUiState.Success -> {
                if (state.topStores.isNotEmpty()) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(
                            items = state.topStores,
                            key = { article -> article.uri.ifBlank { article.url } },
                            contentType = { "article" },
                        ) { article ->
                            NewsItemArticle(article = article, onItemSelected = onItemSelected)
                        }
                    }
                } else {
                    Text(
                        text = "No Articles Found.",
                        fontSize = 12.sp,
                        fontFamily = NotoSansRegular,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }

            is TopStoriesUiState.Failed -> {
                ErrorContent(
                    error = state.error,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
private fun ErrorContent(
    error: AppError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = error.toDisplayMessage(),
            fontSize = 14.sp,
            fontFamily = NotoSansRegular,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onRetry,
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White,
                ),
        ) {
            Text(
                text = "Retry",
                fontSize = 13.sp,
                fontFamily = NotoSansSemiBold,
            )
        }
    }
}

private fun AppError.toDisplayMessage(): String =
    when (this) {
        AppError.NoInternet -> "No internet connection. Please check your network and try again."
        AppError.Timeout -> "Request timed out. Please try again."
        AppError.Unauthorized -> "Unauthorized request. Please check your API key."
        AppError.TooManyRequests -> "Too many requests. Please wait a moment and try again."
        AppError.NotFound -> "No articles found for this section."
        is AppError.ServerError -> "Server error (${code ?: 500}). Please try again later."
        is AppError.Unknown -> message ?: "Something went wrong. Please try again."
    }
