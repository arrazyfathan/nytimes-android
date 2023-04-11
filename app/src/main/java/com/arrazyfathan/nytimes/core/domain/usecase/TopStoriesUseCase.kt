package com.arrazyfathan.nytimes.core.domain.usecase

import com.arrazyfathan.nytimes.core.data.source.Resource
import com.arrazyfathan.nytimes.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
interface TopStoriesUseCase {
    fun getTopStories(section: String): Flow<Resource<List<Article>>>
    fun getAllArticle(): Flow<List<Article>>
    fun getAllBookmarkedArticle(): Flow<List<Article>>
    suspend fun insertArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    fun updateArticle(article: Article, newState: Boolean)

    fun checkArticleIsBookmarked(articleId: String): Flow<Boolean>
}
