package com.arrazyfathan.nytimes.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 11/04/23.
 */

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val topStoriesUseCase: TopStoriesUseCase,
) : ViewModel() {

    fun getAllBookmarkedArticle() = topStoriesUseCase.getAllArticle().asLiveData()
}
