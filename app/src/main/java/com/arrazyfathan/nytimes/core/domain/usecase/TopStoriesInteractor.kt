package com.arrazyfathan.nytimes.core.domain.usecase

import com.arrazyfathan.nytimes.core.data.TopStoriesRepository
import com.arrazyfathan.nytimes.core.data.source.Resource
import com.arrazyfathan.nytimes.core.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
class TopStoriesInteractor @Inject constructor(
    private val topStoriesRepository: TopStoriesRepository,
) : TopStoriesUseCase {

    override fun getTopStories(section: String): Flow<Resource<List<Article>>> {
        return topStoriesRepository.getTopStories(section)
    }

    override fun getAllArticle(): Flow<List<Article>> {
        return topStoriesRepository.getAllSavedArticle()
    }

    override fun getAllBookmarkedArticle(): Flow<List<Article>> {
        return topStoriesRepository.getBookmarkedArticle()
    }

    override suspend fun insertArticle(article: Article) {
        topStoriesRepository.insertArticle(article)
    }

    override suspend fun deleteArticle(article: Article) {
        topStoriesRepository.deleteArticle(article)
    }

    override fun updateArticle(article: Article, newState: Boolean) {
        topStoriesRepository.updateArticle(article, newState)
    }

    override fun checkArticleIsBookmarked(articleId: String): Flow<Boolean> {
        return topStoriesRepository.checkArticleIsBookmarked(articleId)
    }
}
