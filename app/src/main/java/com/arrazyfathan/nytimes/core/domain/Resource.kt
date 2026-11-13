package com.arrazyfathan.nytimes.core.domain

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Error<out T>(val error: AppError, val data: T? = null) : Resource<T>()
}
