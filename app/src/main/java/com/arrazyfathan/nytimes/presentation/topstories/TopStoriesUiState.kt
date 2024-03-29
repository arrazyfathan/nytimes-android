package com.arrazyfathan.nytimes.presentation.topstories

import com.arrazyfathan.nytimes.core.domain.model.Article

/**
 * Created by Ar Razy Fathan Rabbani on 19/06/23.
 */
/*data class TopStoriesUiState(
    val isLoading: Boolean = false,
    val topStories: List<Article>? = emptyList(),
    val errorMessage: String? = null,
)*/

sealed interface TopStoriesUiState {
    object Loading : TopStoriesUiState
    data class Success(val topStores: List<Article>) : TopStoriesUiState
    data class Failed(val message: String) : TopStoriesUiState
}
