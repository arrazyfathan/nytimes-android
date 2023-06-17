package com.arrazyfathan.nytimes.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 11/04/23.
 */

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val topStoriesUseCase: TopStoriesUseCase,
) : ViewModel() {

    private var _state = MutableStateFlow(BookmarkUiState())
    val state: StateFlow<BookmarkUiState> get() = _state

    init {
        getAllBookmarkedArticle()
    }

    private fun getAllBookmarkedArticle() {
        viewModelScope.launch {
            topStoriesUseCase.getAllArticle().collect { articles ->
                _state.update { state ->
                    state.copy(
                        articles = articles,
                    )
                }
            }
        }
    }

    fun selectArticle(article: Article) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedArticle = article,
                )
            }
        }
    }

    fun removeArticle(article: Article) {
        viewModelScope.launch {
            topStoriesUseCase.deleteArticle(article)
            _state.update {
                it.copy(
                    selectedArticle = null,
                )
            }
        }
    }
}
