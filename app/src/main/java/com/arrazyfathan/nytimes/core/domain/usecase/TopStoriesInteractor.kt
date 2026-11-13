package com.arrazyfathan.nytimes.core.domain.usecase

import com.arrazyfathan.nytimes.core.domain.Resource
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.repository.ITopStoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
class TopStoriesInteractor
    @Inject
    constructor(
        private val topStoriesRepository: ITopStoriesRepository,
    ) : TopStoriesUseCase {
        override fun getTopStories(section: String): Flow<Resource<List<Article>>> = topStoriesRepository.getTopStories(section)

        override fun getAllArticle(): Flow<List<Article>> = topStoriesRepository.getAllSavedArticle()

        override fun getAllBookmarkedArticle(): Flow<List<Article>> = topStoriesRepository.getBookmarkedArticle()

        override suspend fun insertArticle(article: Article) {
            topStoriesRepository.insertArticle(article)
        }

        override suspend fun deleteArticle(article: Article) {
            topStoriesRepository.deleteArticle(article)
        }

        override fun updateArticle(
            article: Article,
            newState: Boolean,
        ) {
            topStoriesRepository.updateArticle(article, newState)
        }

        override fun checkArticleIsBookmarked(articleId: String): Flow<Boolean> = topStoriesRepository.checkArticleIsBookmarked(articleId)
    }
