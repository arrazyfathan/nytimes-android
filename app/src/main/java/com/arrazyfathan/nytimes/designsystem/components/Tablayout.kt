package com.arrazyfathan.nytimes.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    state: TopStoriesUiState,
    onItemSelected: (Article) -> Unit,
    onRetry: () -> Unit = {},
) {
    Column(modifier = modifier.background(Color.White)) {
        Tabs(pagerState = pagerState)
        TabsContent(
            pagerState = pagerState,
            state = state,
            onItemSelected = onItemSelected,
            onRetry = onRetry,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(
    pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                height = 2.dp,
                color = Color.White,
            )
        },
    ) {
        sections.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        sections[index],
                        color = if (pagerState.currentPage == index) Color.Black else Color.LightGray,
                        fontSize = 14.sp,
                        fontFamily = NotoSansSemiBold,
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(
    pagerState: PagerState,
    state: TopStoriesUiState,
    onItemSelected: (Article) -> Unit,
    onRetry: () -> Unit = {},
) {
    HorizontalPager(
        state = pagerState,
    ) {
        ContentSection(
            state = state,
            onItemSelected = onItemSelected,
            onRetry = onRetry,
        )
    }
}

@Composable
fun ContentSection(
    state: TopStoriesUiState,
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
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(state.topStores) { article ->
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
            colors = ButtonDefaults.buttonColors(
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

private fun AppError.toDisplayMessage(): String = when (this) {
    AppError.NoInternet -> "No internet connection. Please check your network and try again."
    AppError.Timeout -> "Request timed out. Please try again."
    AppError.Unauthorized -> "Unauthorized request. Please check your API key."
    AppError.TooManyRequests -> "Too many requests. Please wait a moment and try again."
    AppError.NotFound -> "No articles found for this section."
    is AppError.ServerError -> "Server error (${code ?: 500}). Please try again later."
    is AppError.Unknown -> message ?: "Something went wrong. Please try again."
}
