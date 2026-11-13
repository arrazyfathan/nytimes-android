package com.arrazyfathan.nytimes.core.data.source.remote

import com.arrazyfathan.nytimes.core.data.source.remote.network.ApiResponse
import com.arrazyfathan.nytimes.core.data.source.remote.network.KEY_NO_INTERNET
import com.arrazyfathan.nytimes.core.data.source.remote.network.KEY_UNAUTHORIZED
import com.arrazyfathan.nytimes.core.data.source.remote.network.TopStoriesAPI
import com.arrazyfathan.nytimes.core.data.source.remote.response.ArticleDto
import com.arrazyfathan.nytimes.core.data.source.remote.response.TopStoriesDto
import com.arrazyfathan.nytimes.core.domain.AppError
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
class RemoteDataSource @Inject constructor(private val topStoriesAPI: TopStoriesAPI) {

    fun getTopStoriesArticle(section: String): Flow<ApiResponse<List<ArticleDto>>> = flow {
        try {
            val response = topStoriesAPI.getTopStories(section)
            emit(handleResponse(response))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(ApiResponse.Error(mapExceptionToError(e)))
        }
    }.flowOn(Dispatchers.IO)

    private fun handleResponse(response: Response<TopStoriesDto>): ApiResponse<List<ArticleDto>> {
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            val articles = body.articleDtos
            if (articles.isEmpty()) {
                ApiResponse.Empty
            } else {
                ApiResponse.Success(articles)
            }
        } else {
            ApiResponse.Error(mapHttpCodeToError(response.code()))
        }
    }

    private fun mapExceptionToError(throwable: Throwable): AppError {
        return when (throwable) {
            is UnknownHostException, is ConnectException -> AppError.NoInternet
            is SocketTimeoutException -> AppError.Timeout
            is HttpException -> mapHttpCodeToError(throwable.code())
            is IOException -> AppError.NoInternet
            else -> AppError.Unknown(throwable.localizedMessage)
        }
    }

    private fun mapHttpCodeToError(responseCode: Int): AppError {
        return when (responseCode) {
            KEY_NO_INTERNET -> AppError.NoInternet
            KEY_UNAUTHORIZED, 403 -> AppError.Unauthorized
            404 -> AppError.NotFound
            429 -> AppError.TooManyRequests
            in 500..599 -> AppError.ServerError(responseCode)
            else -> AppError.Unknown("HTTP $responseCode")
        }
    }
}
