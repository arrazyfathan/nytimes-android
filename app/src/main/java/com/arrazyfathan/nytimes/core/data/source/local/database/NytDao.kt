package com.arrazyfathan.nytimes.core.data.source.local.database

import androidx.room.*
import com.arrazyfathan.nytimes.core.data.source.local.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ar Razy Fathan Rabbani on 11/04/23.
 */

@Dao
interface NytDao {
    @Upsert
    suspend fun upsert(article: ArticleEntity)

    @Query("SELECT * FROM articles")
    fun getAllArticle(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles where isBookmarked = 1")
    fun getBookmarkedArticle(): Flow<List<ArticleEntity>>

    @Delete
    suspend fun delete(article: ArticleEntity)

    @Update
    fun updateSavedArticle(article: ArticleEntity)

    @Query("SELECT EXISTS (SELECT * FROM articles WHERE articleId = :articleId)")
    fun checkArticleIsBookmarked(articleId: String): Flow<Boolean>
}
