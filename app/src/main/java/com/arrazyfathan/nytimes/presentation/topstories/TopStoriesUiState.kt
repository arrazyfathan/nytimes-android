package com.arrazyfathan.nytimes.presentation.topstories

import com.arrazyfathan.nytimes.core.domain.AppError
import com.arrazyfathan.nytimes.core.domain.model.Article

/**
 * Created by Ar Razy Fathan Rabbani on 19/06/23.
 */
sealed interface TopStoriesUiState {
    data object Loading : TopStoriesUiState
    data class Success(val topStores: List<Article>) : TopStoriesUiState
    data class Failed(val error: AppError) : TopStoriesUiState
}
