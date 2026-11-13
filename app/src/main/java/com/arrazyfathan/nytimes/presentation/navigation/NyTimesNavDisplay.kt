package com.arrazyfathan.nytimes.presentation.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.presentation.article.ArticleDetailScreen
import com.arrazyfathan.nytimes.presentation.article.ArticleDetailViewModel
import com.arrazyfathan.nytimes.presentation.bookmark.BookmarkScreen
import com.arrazyfathan.nytimes.presentation.bookmark.BookmarkViewModel
import com.arrazyfathan.nytimes.presentation.topstories.HomeScreen
import com.arrazyfathan.nytimes.presentation.topstories.TopStoriesViewModel
import com.arrazyfathan.nytimes.presentation.topstories.sections
import com.arrazyfathan.nytimes.utils.launchUrl

private const val TRANSITION_DURATION_MS = 300

@Composable
fun NyTimesNavDisplay(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(TopStoriesRoute)
    val context = LocalContext.current

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION_MS),
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION_MS),
            )
        },
        popTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION_MS),
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION_MS),
            )
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION_MS),
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION_MS),
            )
        },
        entryProvider = entryProvider {
            entry<TopStoriesRoute> {
                val viewModel: TopStoriesViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = viewModel,
                    onBookmarkClicked = {
                        backStack.add(BookmarkRoute)
                    },
                    sections = sections,
                    onItemSelected = { article ->
                        backStack.add(ArticleDetailRoute(article))
                    },
                )
            }
            entry<BookmarkRoute> {
                val viewModel: BookmarkViewModel = hiltViewModel()
                BookmarkScreen(
                    viewModel = viewModel,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    onClicked = { article ->
                        backStack.add(ArticleDetailRoute(article))
                    },
                )
            }
            entry<ArticleDetailRoute> { key ->
                val viewModel: ArticleDetailViewModel = hiltViewModel()
                ArticleDetailScreen(
                    viewModel = viewModel,
                    article = key.article,
                    onShareClicked = { context.shareArticle(key.article) },
                    openLink = { context.launchUrl(key.article.shortUrl) },
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                )
            }
        },
    )
}

private fun Context.shareArticle(article: Article) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            """
            ${article.title}
            ${article.description}
            ${article.shortUrl}
            """.trimIndent(),
        )
        type = "text/plain"
    }
    startActivity(shareIntent)
}
