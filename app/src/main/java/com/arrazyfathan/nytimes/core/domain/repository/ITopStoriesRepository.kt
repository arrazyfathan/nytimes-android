package com.arrazyfathan.nytimes.core.domain.repository

import com.arrazyfathan.nytimes.core.data.source.Resource
import com.arrazyfathan.nytimes.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
interface ITopStoriesRepository {
    fun getTopStories(section: String): Flow<Resource<List<Article>>>
    fun getAllSavedArticle(): Flow<List<Article>>

    fun getBookmarkedArticle(): Flow<List<Article>>

    suspend fun insertArticle(article: Article)

    fun updateArticle(article: Article, newState: Boolean)

    suspend fun deleteArticle(article: Article)

    fun checkArticleIsBookmarked(articleId: String): Flow<Boolean>
}
