package com.arrazyfathan.nytimes.core.data

import com.arrazyfathan.nytimes.core.data.source.Resource
import com.arrazyfathan.nytimes.core.data.source.remote.RemoteDataSource
import com.arrazyfathan.nytimes.core.data.source.remote.network.ApiResponse
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.repository.ITopStoriesRepository
import com.arrazyfathan.nytimes.core.utils.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
class TopStoriesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : ITopStoriesRepository {

    override fun getTopStories(section: String): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading())
            when (val response = remoteDataSource.getTopStoriesArticle(section).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(response.data.map { it.mapToDomain() }))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error(message = "Data not found"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
            }
        }
    }
}
