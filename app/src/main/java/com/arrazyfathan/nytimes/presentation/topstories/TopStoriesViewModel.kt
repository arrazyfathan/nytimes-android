package com.arrazyfathan.nytimes.presentation.topstories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrazyfathan.nytimes.core.domain.AppError
import com.arrazyfathan.nytimes.core.domain.Resource
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopStoriesViewModel @Inject constructor(
    private val topStoriesUseCase: TopStoriesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<TopStoriesUiState>(TopStoriesUiState.Loading)
    val state: StateFlow<TopStoriesUiState> = _state.asStateFlow()

    fun getTopStories(section: String) {
        viewModelScope.launch {
            topStoriesUseCase.getTopStories(section)
                .catch { throwable ->
                    if (throwable is CancellationException) throw throwable
                    _state.update {
                        TopStoriesUiState.Failed(AppError.Unknown(throwable.localizedMessage))
                    }
                }
                .collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _state.update { TopStoriesUiState.Loading }
                        }

                        is Resource.Success -> {
                            _state.update {
                                TopStoriesUiState.Success(
                                    resource.data.filter { it.title.isNotBlank() },
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update { TopStoriesUiState.Failed(resource.error) }
                        }
                    }
                }
        }
    }
}
