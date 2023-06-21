package com.arrazyfathan.nytimes.presentation.bookmark

import com.arrazyfathan.nytimes.core.domain.model.Article

/**
 * Created by Ar Razy Fathan Rabbani on 17/06/23.
 */

data class BookmarkUiState(
    val articles: List<Article> = emptyList(),
    val selectedArticle: Article? = null,
)
