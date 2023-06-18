package com.arrazyfathan.nytimes.presentation.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 11/04/23.
 */

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val topStoriesUseCase: TopStoriesUseCase,
) : ViewModel() {

    private var _state = MutableSharedFlow<ArticleDetailUiState>()
    val state: SharedFlow<ArticleDetailUiState> get() = _state

    fun checkArticleIsBookmarked(articleId: String) =
        topStoriesUseCase.checkArticleIsBookmarked(articleId)

    fun bookmarkArticle(article: Article) {
        viewModelScope.launch {
            topStoriesUseCase.insertArticle(article)
        }
    }

    fun removeBookmark(article: Article) {
        viewModelScope.launch {
            topStoriesUseCase.deleteArticle(article)
        }
    }
}
