package com.arrazyfathan.nytimes.presentation.topstories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.designsystem.components.TabLayout
import com.arrazyfathan.nytimes.designsystem.components.pressClickEffect
import com.arrazyfathan.nytimes.designsystem.theme.DomineBold
import com.arrazyfathan.nytimes.designsystem.theme.SyncStatusBarWithAppBar
import kotlinx.coroutines.flow.collect

/**
 * Created by Ar Razy Fathan Rabbani on 19/06/23.
 */

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    viewModel: TopStoriesViewModel,
    onBookmarkClicked: () -> Unit,
    sections: List<String>,
    onItemSelected: (Article) -> Unit,
) {
    val appBarColor = Color.White

    SyncStatusBarWithAppBar(appBarColor)

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { sections.size },
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.getTopStories(sections[page].lowercase())
        }
    }

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                contentPadding = PaddingValues(16.dp),
                backgroundColor = appBarColor,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Top Stories",
                        fontSize = 24.sp,
                        fontFamily = DomineBold,
                        color = Color.Black,
                    )
                    IconButton(
                        onClick = onBookmarkClicked,
                        modifier = Modifier.pressClickEffect(),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bookmarks),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp),
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        TabLayout(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .navigationBarsPadding(),
            pagerState = pagerState,
            state = state,
            onItemSelected = onItemSelected,
            onRetry = {
                viewModel.getTopStories(sections[pagerState.currentPage].lowercase())
            },
        )
    }
}
