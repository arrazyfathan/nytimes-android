package com.arrazyfathan.nytimes.presentation.navigation

import androidx.navigation3.runtime.NavKey
import com.arrazyfathan.nytimes.core.domain.model.Article
import kotlinx.serialization.Serializable

@Serializable
data object TopStoriesRoute : NavKey

@Serializable
data object BookmarkRoute : NavKey

@Serializable
data class ArticleDetailRoute(val article: Article) : NavKey
