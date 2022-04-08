package com.arrazyfathan.nytimes.repository

import com.arrazyfathan.nytimes.api.network.RetrofitInstance
import com.arrazyfathan.nytimes.data.local.ArticleDatabase
import com.arrazyfathan.nytimes.data.model.Article

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getTopStories() = RetrofitInstance.api.getTopStories()

    suspend fun insert(article: Article) = db.getArticleDao().insert(article)

    fun getSavedArticle() = db.getArticleDao().getSavedArticles()

    suspend fun deleteSavedArticle(article: Article) = db.getArticleDao().deleteArticle(article)

}