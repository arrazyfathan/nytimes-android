package com.arrazyfathan.nytimes.presentation.topstories

import androidx.lifecycle.*
import com.arrazyfathan.nytimes.core.data.source.Resource
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopStoriesViewModel @Inject constructor(
    private val topStoriesUseCase: TopStoriesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TopStoriesUiState())
    val state: StateFlow<TopStoriesUiState> get() = _state

    private val _topStories = MutableLiveData<Resource<List<Article>>>()
    val topStories: LiveData<Resource<List<Article>>> get() = _topStories

    fun getTopStories(section: String) {
        viewModelScope.launch {
            /*topStoriesUseCase.getTopStories(section).collect {
                _topStories.postValue(it)
            }*/

            topStoriesUseCase.getTopStories(section).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true,
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                topStories = resource.data,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = resource.message,
                            )
                        }
                    }
                }
            }
        }
    }
}
