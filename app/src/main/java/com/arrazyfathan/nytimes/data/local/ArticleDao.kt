package com.arrazyfathan.nytimes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arrazyfathan.nytimes.data.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getSavedArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}