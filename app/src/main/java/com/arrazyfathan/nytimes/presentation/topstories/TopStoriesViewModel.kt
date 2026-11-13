package com.arrazyfathan.nytimes.presentation.topstories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrazyfathan.nytimes.core.domain.AppError
import com.arrazyfathan.nytimes.core.domain.Resource
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopStoriesViewModel
    @Inject
    constructor(
        private val topStoriesUseCase: TopStoriesUseCase,
    ) : ViewModel() {
        private val _sectionStates = MutableStateFlow<Map<String, TopStoriesUiState>>(emptyMap())
        val sectionStates: StateFlow<Map<String, TopStoriesUiState>> = _sectionStates.asStateFlow()

        private val loadingJobs = mutableMapOf<String, Job>()

        fun getTopStories(
            section: String,
            forceRefresh: Boolean = false,
        ) {
            val key = section.lowercase()
            val currentState = _sectionStates.value[key]

            if (!forceRefresh) {
                if (currentState is TopStoriesUiState.Success) return
                if (loadingJobs[key]?.isActive == true) return
            }

            loadingJobs[key]?.cancel()
            loadingJobs[key] =
                viewModelScope.launch {
                    topStoriesUseCase
                        .getTopStories(key)
                        .catch { throwable ->
                            if (throwable is CancellationException) throw throwable
                            updateSectionState(
                                key,
                                TopStoriesUiState.Failed(AppError.Unknown(throwable.localizedMessage)),
                            )
                        }.collect { resource ->
                            val newState =
                                when (resource) {
                                    is Resource.Loading -> {
                                        TopStoriesUiState.Loading
                                    }

                                    is Resource.Success -> {
                                        TopStoriesUiState.Success(
                                            resource.data.filter { it.title.isNotBlank() },
                                        )
                                    }

                                    is Resource.Error -> {
                                        TopStoriesUiState.Failed(resource.error)
                                    }
                                }
                            updateSectionState(key, newState)
                        }
                }
        }

        private fun updateSectionState(
            section: String,
            state: TopStoriesUiState,
        ) {
            _sectionStates.update { current ->
                current + (section to state)
            }
        }
    }
