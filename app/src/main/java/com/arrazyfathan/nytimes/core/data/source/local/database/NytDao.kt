package com.arrazyfathan.nytimes.core.data.source.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.arrazyfathan.nytimes.core.data.source.local.entities.ArticleEntity
import com.arrazyfathan.nytimes.core.data.source.local.entities.CachedArticleEntity
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

    @Query("SELECT * FROM cached_articles WHERE querySection = :section ORDER BY position ASC")
    fun getCachedTopStories(section: String): Flow<List<CachedArticleEntity>>

    @Upsert
    suspend fun upsertCachedTopStories(articles: List<CachedArticleEntity>)

    @Query("DELETE FROM cached_articles WHERE querySection = :section")
    suspend fun clearCachedTopStories(section: String)

    @Transaction
    suspend fun replaceCachedTopStories(
        section: String,
        articles: List<CachedArticleEntity>,
    ) {
        clearCachedTopStories(section)
        upsertCachedTopStories(articles)
    }
}
