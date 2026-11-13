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
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.presentation.article.ArticleDetailScreen
import com.arrazyfathan.nytimes.presentation.bookmark.BookmarkScreen
import com.arrazyfathan.nytimes.presentation.bookmark.BookmarkViewModel
import com.arrazyfathan.nytimes.presentation.topstories.HomeScreen
import com.arrazyfathan.nytimes.presentation.topstories.sections
import com.arrazyfathan.nytimes.utils.launchUrl

private const val TRANSITION_DURATION_MS = 300

@Composable
fun NyTimesNavDisplay(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(TopStoriesRoute)
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
    val context = LocalContext.current

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        sceneStrategies = listOf(listDetailStrategy),
        modifier = modifier,
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION_MS),
            ) togetherWith
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(TRANSITION_DURATION_MS),
                )
        },
        popTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION_MS),
            ) togetherWith
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(TRANSITION_DURATION_MS),
                )
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION_MS),
            ) togetherWith
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(TRANSITION_DURATION_MS),
                )
        },
        entryProvider =
            entryProvider {
                entry<TopStoriesRoute>(
                    metadata = ListDetailScene.listPane(),
                ) {
                    HomeScreen(
                        onBookmarkClicked = {
                            backStack.add(BookmarkRoute)
                        },
                        sections = sections,
                        onItemSelected = { article ->
                            backStack.addDetail(ArticleDetailRoute(article))
                        },
                    )
                }
                entry<BookmarkRoute>(
                    metadata = ListDetailScene.listPane(),
                ) {
                    val viewModel: BookmarkViewModel = hiltViewModel()
                    BookmarkScreen(
                        viewModel = viewModel,
                        modifier = Modifier.background(Color.White).fillMaxSize(),
                        onClicked = { article ->
                            backStack.addDetail(ArticleDetailRoute(article))
                        },
                    )
                }
                entry<ArticleDetailRoute>(
                    metadata = ListDetailScene.detailPane(),
                ) { key ->
                    ArticleDetailScreen(
                        article = key.article,
                        onShareClicked = { context.shareArticle(key.article) },
                        openLink = {
                            context.launchUrl(key.article.url.ifBlank { key.article.shortUrl })
                        },
                        modifier = Modifier.background(Color.White).fillMaxSize(),
                    )
                }
            },
    )
}

private fun NavBackStack<NavKey>.addDetail(detailRoute: ArticleDetailRoute) {
    removeIf { it is ArticleDetailRoute }
    add(detailRoute)
}

private fun Context.shareArticle(article: Article) {
    val articleLink = article.shortUrl.ifBlank { article.url }
    val shareIntent =
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                """
                ${article.title}
                ${article.description}
                $articleLink
                """.trimIndent(),
            )
            type = "text/plain"
        }
    startActivity(shareIntent)
}
