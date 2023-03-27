package com.arrazyfathan.nytimes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */

@HiltViewModel
class TryingViewModel @Inject constructor(
    topStoriesUseCase: TopStoriesUseCase,
) : ViewModel() {

    val topStories = topStoriesUseCase.getTopStories().asLiveData()
}
