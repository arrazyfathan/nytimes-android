package com.arrazyfathan.nytimes.presentation.topstories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import kotlinx.coroutines.flow.collect

/**
 * Created by Ar Razy Fathan Rabbani on 19/06/23.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: TopStoriesViewModel,
    onBookmarkClicked: () -> Unit,
    sections: List<String>,
    onItemSelected: (Article) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            when (page) {
                0 -> viewModel.getTopStories(sections[page].lowercase())
                1 -> viewModel.getTopStories(sections[page].lowercase())
                2 -> viewModel.getTopStories(sections[page].lowercase())
                3 -> viewModel.getTopStories(sections[page].lowercase())
                4 -> viewModel.getTopStories(sections[page].lowercase())
                5 -> viewModel.getTopStories(sections[page].lowercase())
                6 -> viewModel.getTopStories(sections[page].lowercase())
                7 -> viewModel.getTopStories(sections[page].lowercase())
                8 -> viewModel.getTopStories(sections[page].lowercase())
                9 -> viewModel.getTopStories(sections[page].lowercase())
                10 -> viewModel.getTopStories(sections[page].lowercase())
                11 -> viewModel.getTopStories(sections[page].lowercase())
                12 -> viewModel.getTopStories(sections[page].lowercase())
                13 -> viewModel.getTopStories(sections[page].lowercase())
                14 -> viewModel.getTopStories(sections[page].lowercase())
                15 -> viewModel.getTopStories(sections[page].lowercase())
            }
        }
    }

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                contentPadding = PaddingValues(16.dp),
                backgroundColor = Color.White,
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
            modifier = Modifier.padding(paddingValues),
            pagerState,
            state,
            onItemSelected,
        )
    }
}
