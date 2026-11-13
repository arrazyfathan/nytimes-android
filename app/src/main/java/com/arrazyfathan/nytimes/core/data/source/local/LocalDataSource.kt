package com.arrazyfathan.nytimes.core.data.source.local

import com.arrazyfathan.nytimes.core.data.source.local.database.NytDao
import com.arrazyfathan.nytimes.core.data.source.local.entities.ArticleEntity
import com.arrazyfathan.nytimes.core.data.source.local.entities.CachedArticleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
@Singleton
class LocalDataSource
    @Inject
    constructor(
        private val nytDao: NytDao,
    ) {
        fun getAllArticle(): Flow<List<ArticleEntity>> = nytDao.getAllArticle()

        fun getBookmarkedArticle(): Flow<List<ArticleEntity>> = nytDao.getBookmarkedArticle()

        suspend fun insertArticle(articleEntity: ArticleEntity) {
            nytDao.deleteById("")
            nytDao.upsert(articleEntity)
        }

        suspend fun deleteArticle(articleEntity: ArticleEntity) {
            nytDao.deleteById(articleEntity.articleId)
        }

        fun updateArticle(
            articleEntity: ArticleEntity,
            newState: Boolean,
        ) {
            articleEntity.isBookmarked = newState
            nytDao.updateSavedArticle(articleEntity)
        }

        fun checkArticleIsBookmarked(articleId: String): Flow<Boolean> = nytDao.checkArticleIsBookmarked(articleId)

        fun getCachedTopStories(section: String): Flow<List<CachedArticleEntity>> = nytDao.getCachedTopStories(section)

        suspend fun replaceCachedTopStories(
            section: String,
            articles: List<CachedArticleEntity>,
        ) {
            nytDao.replaceCachedTopStories(section, articles)
        }
    }
