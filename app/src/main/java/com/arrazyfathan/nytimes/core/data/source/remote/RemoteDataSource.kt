package com.arrazyfathan.nytimes.core.data.source.remote

import com.arrazyfathan.nytimes.core.data.source.remote.network.*
import com.arrazyfathan.nytimes.core.data.source.remote.response.ArticleDto
import com.arrazyfathan.nytimes.core.data.source.remote.response.TopStoriesDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
class RemoteDataSource @Inject constructor(private val topStoriesAPI: TopStoriesAPI) {

    suspend fun getTopStoriesArticle(section: String): Flow<ApiResponse<List<ArticleDto>>> {
        return flow {
            try {
                val response = topStoriesAPI.getTopStories(section)
                emit(handleResponse(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun handleResponse(response: Response<TopStoriesDto>): ApiResponse<List<ArticleDto>> {
        return if (response.isSuccessful && response.body() != null) {
            val data = response.body()!!.articleDtos
            ApiResponse.Success(data)
        } else {
            val error = getResponseError(response.code())
            ApiResponse.Error(error)
        }
    }

    private fun getResponseError(responseCode: Int): String {
        return when (responseCode) {
            KEY_NO_INTERNET -> MessageResult.NO_CONNECTION
            KEY_SERVER_ERROR -> MessageResult.SERVER_ERROR
            429 -> "To many request"
            else -> "Unknown error"
        }
    }
}
