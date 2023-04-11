package com.arrazyfathan.nytimes.presentation.topstories

import androidx.lifecycle.*
import com.arrazyfathan.nytimes.core.data.source.Resource
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopStoriesViewModel @Inject constructor(
    private val topStoriesUseCase: TopStoriesUseCase,
) : ViewModel() {

    private val _topStories = MutableLiveData<Resource<List<Article>>>()
    val topStories: LiveData<Resource<List<Article>>> get() = _topStories

    fun getTopStories(section: String) {
        viewModelScope.launch {
            topStoriesUseCase.getTopStories(section).collect {
                _topStories.postValue(it)
            }
        }
    }
}
