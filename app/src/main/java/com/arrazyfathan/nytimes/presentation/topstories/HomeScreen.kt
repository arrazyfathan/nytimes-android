package com.arrazyfathan.nytimes.presentation.topstories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
    onBookmarkClicked: () -> Unit,
    sections: List<String>,
    onItemSelected: (Article) -> Unit,
) {
    val viewModel: TopStoriesViewModel = hiltViewModel()
    val appBarColor = Color.White

    SyncStatusBarWithAppBar(appBarColor)

    val pagerState =
        rememberPagerState(
            initialPage = 0,
            pageCount = { sections.size },
        )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.getTopStories(sections[page].lowercase())
        }
    }

    val sectionStates by viewModel.sectionStates.collectAsState()

    Scaffold(
        topBar = {
            Surface(
                color = appBarColor,
                elevation = 0.dp,
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
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
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        TabLayout(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues)
                    .navigationBarsPadding(),
            pagerState = pagerState,
            sectionStates = sectionStates,
            onItemSelected = onItemSelected,
            onRetry = { section ->
                viewModel.getTopStories(section, forceRefresh = true)
            },
        )
    }
}
