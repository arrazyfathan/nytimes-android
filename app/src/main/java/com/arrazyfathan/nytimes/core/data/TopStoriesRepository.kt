package com.arrazyfathan.nytimes.core.data

import com.arrazyfathan.nytimes.core.data.source.Resource
import com.arrazyfathan.nytimes.core.data.source.local.LocalDataSource
import com.arrazyfathan.nytimes.core.data.source.remote.RemoteDataSource
import com.arrazyfathan.nytimes.core.data.source.remote.network.ApiResponse
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.repository.ITopStoriesRepository
import com.arrazyfathan.nytimes.core.utils.mapToDomain
import com.arrazyfathan.nytimes.core.utils.mapToEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */

@Singleton
class TopStoriesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
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

    override fun getAllSavedArticle(): Flow<List<Article>> {
        return localDataSource.getAllArticle().map { articles ->
            articles.map { it.mapToDomain() }
        }
    }

    override fun getBookmarkedArticle(): Flow<List<Article>> {
        return localDataSource.getBookmarkedArticle().map { articles ->
            articles.map { it.mapToDomain() }
        }
    }

    override suspend fun insertArticle(article: Article) {
        withContext(Dispatchers.IO) {
            localDataSource.insertArticle(article.mapToEntity())
        }
    }

    override fun updateArticle(article: Article, newState: Boolean) {
        localDataSource.updateArticle(article.mapToEntity(), newState)
    }

    override suspend fun deleteArticle(article: Article) = withContext(Dispatchers.IO) {
        return@withContext localDataSource.deleteArticle(article.mapToEntity())
    }

    override fun checkArticleIsBookmarked(articleId: String): Flow<Boolean> {
        return localDataSource.checkArticleIsBookmarked(articleId)
    }
}
