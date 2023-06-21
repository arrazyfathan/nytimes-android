package com.arrazyfathan.nytimes.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrazyfathan.nytimes.core.data.source.remote.network.MessageResult
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
) {
    Column(modifier = modifier.background(Color.White)) {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, state, onItemSelected)
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
) {
    HorizontalPager(
        state = pagerState,
        pageCount = sections.size,
    ) { page ->
        when (page) {
            0 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            1 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            2 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            3 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            4 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            5 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            6 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            7 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            8 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            9 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            10 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            11 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            12 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            13 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            14 -> ContentSection(pagerState = pagerState, state, onItemSelected)
            15 -> ContentSection(pagerState = pagerState, state, onItemSelected)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentSection(
    pagerState: PagerState,
    state: TopStoriesUiState,
    onItemSelected: (Article) -> Unit,
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
                when (state.message) {
                    MessageResult.NO_CONNECTION -> {
                        Text(
                            text = "No Internet Connection",
                            fontSize = 12.sp,
                            fontFamily = NotoSansRegular,
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }

                    else -> {
                        Text(
                            text = state.message,
                            fontSize = 12.sp,
                            fontFamily = NotoSansRegular,
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }
                }
            }
        }
    }
}
