package com.arrazyfathan.nytimes.presentation.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 11/04/23.
 */

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val topStoriesUseCase: TopStoriesUseCase,
) : ViewModel() {

    fun checkArticleIsBookmarked(articleId: String) =
        topStoriesUseCase.checkArticleIsBookmarked(articleId).asLiveData()

    fun bookmarkArticle(article: Article) {
        viewModelScope.launch {
            topStoriesUseCase.insertArticle(article)
        }
    }
}
