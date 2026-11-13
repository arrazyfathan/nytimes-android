package com.arrazyfathan.nytimes.core.data

import com.arrazyfathan.nytimes.core.data.source.local.LocalDataSource
import com.arrazyfathan.nytimes.core.data.source.remote.RemoteDataSource
import com.arrazyfathan.nytimes.core.data.source.remote.network.ApiResponse
import com.arrazyfathan.nytimes.core.domain.AppError
import com.arrazyfathan.nytimes.core.domain.Resource
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.repository.ITopStoriesRepository
import com.arrazyfathan.nytimes.core.utils.mapToCachedEntity
import com.arrazyfathan.nytimes.core.utils.mapToDomain
import com.arrazyfathan.nytimes.core.utils.mapToEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
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
class TopStoriesRepository
    @Inject
    constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
    ) : ITopStoriesRepository {
        override fun getTopStories(section: String): Flow<Resource<List<Article>>> {
            val normalizedSection = section.lowercase()
            return flow {
                val cachedEntities = localDataSource.getCachedTopStories(normalizedSection).first()
                if (cachedEntities.isNotEmpty()) {
                    emit(Resource.Success(cachedEntities.map { it.mapToDomain() }))
                } else {
                    emit(Resource.Loading())
                }

                when (val response = remoteDataSource.getTopStoriesArticle(normalizedSection).first()) {
                    is ApiResponse.Success -> {
                        val validDtos = response.data.filter { !it.title.isNullOrBlank() }
                        val entities =
                            validDtos.mapIndexed { index, dto ->
                                dto.mapToCachedEntity(
                                    querySection = normalizedSection,
                                    position = index,
                                )
                            }
                        localDataSource.replaceCachedTopStories(normalizedSection, entities)
                        emitAll(
                            localDataSource.getCachedTopStories(normalizedSection).map { list ->
                                Resource.Success(list.map { it.mapToDomain() })
                            },
                        )
                    }

                    is ApiResponse.Empty -> {
                        if (cachedEntities.isEmpty()) {
                            emit(Resource.Error(AppError.NotFound))
                        } else {
                            emitAll(
                                localDataSource.getCachedTopStories(normalizedSection).map { list ->
                                    Resource.Success(list.map { it.mapToDomain() })
                                },
                            )
                        }
                    }

                    is ApiResponse.Error -> {
                        if (cachedEntities.isEmpty()) {
                            emit(Resource.Error(response.error))
                        } else {
                            emitAll(
                                localDataSource.getCachedTopStories(normalizedSection).map { list ->
                                    Resource.Success(list.map { it.mapToDomain() })
                                },
                            )
                        }
                    }
                }
            }.catch { throwable ->
                if (throwable is CancellationException) throw throwable
                val fallback =
                    runCatching {
                        localDataSource.getCachedTopStories(normalizedSection).first()
                    }.getOrNull().orEmpty()
                if (fallback.isNotEmpty()) {
                    emit(Resource.Success(fallback.map { it.mapToDomain() }))
                } else {
                    emit(Resource.Error(AppError.Unknown(throwable.localizedMessage)))
                }
            }
        }

        override fun getAllSavedArticle(): Flow<List<Article>> =
            localDataSource.getAllArticle().map { articles ->
                articles.map { it.mapToDomain() }
            }

        override fun getBookmarkedArticle(): Flow<List<Article>> =
            localDataSource.getBookmarkedArticle().map { articles ->
                articles.map { it.mapToDomain() }
            }

        override suspend fun insertArticle(article: Article) {
            withContext(Dispatchers.IO) {
                localDataSource.insertArticle(article.mapToEntity())
            }
        }

        override fun updateArticle(
            article: Article,
            newState: Boolean,
        ) {
            localDataSource.updateArticle(article.mapToEntity(), newState)
        }

        override suspend fun deleteArticle(article: Article) =
            withContext(Dispatchers.IO) {
                return@withContext localDataSource.deleteArticle(article.mapToEntity())
            }

        override fun checkArticleIsBookmarked(articleId: String): Flow<Boolean> = localDataSource.checkArticleIsBookmarked(articleId)
    }
