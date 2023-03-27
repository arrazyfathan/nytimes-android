package com.arrazyfathan.nytimes.core.domain.usecase

import com.arrazyfathan.nytimes.core.data.TopStoriesRepository
import com.arrazyfathan.nytimes.core.data.source.Resource
import com.arrazyfathan.nytimes.core.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
class TopStoriesInteractor @Inject constructor(
    private val topStoriesRepository: TopStoriesRepository,
) : TopStoriesUseCase {

    override fun getTopStories(): Flow<Resource<List<Article>>> {
        return topStoriesRepository.getTopStories()
    }
}
