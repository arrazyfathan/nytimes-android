package com.arrazyfathan.nytimes.core.data.source.remote.network

import com.arrazyfathan.nytimes.core.domain.AppError

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: AppError) : ApiResponse<Nothing>()
    data object Empty : ApiResponse<Nothing>()
}
