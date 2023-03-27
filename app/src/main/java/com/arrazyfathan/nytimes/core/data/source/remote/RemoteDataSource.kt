package com.arrazyfathan.nytimes.core.data.source.remote

import com.arrazyfathan.nytimes.core.data.source.remote.network.ApiResponse
import com.arrazyfathan.nytimes.core.data.source.remote.network.TopStoriesAPI
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

    suspend fun getTopStoriesArticle(): Flow<ApiResponse<List<ArticleDto>>> {
        return flow {
            try {
                val response = topStoriesAPI.getTopStories()
                emit(handleResponse(response))
            } catch (e: Exception) {
                e.printStackTrace()
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
        val strResponseCode = responseCode.toString()
        return when (strResponseCode.drop(0)) {
            "4" -> "Please check your internet"
            "5" -> "Server error"
            else -> "Unknown error"
        }
    }
}
