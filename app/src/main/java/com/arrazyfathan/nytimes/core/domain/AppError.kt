package com.arrazyfathan.nytimes.core.domain

sealed interface AppError {
    data object NoInternet : AppError
    data object Timeout : AppError
    data object Unauthorized : AppError
    data object TooManyRequests : AppError
    data object NotFound : AppError
    data class ServerError(val code: Int? = null) : AppError
    data class Unknown(val message: String? = null) : AppError
}
