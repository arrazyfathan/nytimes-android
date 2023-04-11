package com.arrazyfathan.nytimes.core.data.source.remote.network

object MessageResult {
    const val NO_CONNECTION = "NO_CONECTION"
    const val SERVER_ERROR = "SERVER_ERROR"
    const val UNAUTHORIZED = "EXPIRED_TOKEN"

    fun getMessage(statusCode: Int): String {
        when (statusCode) {
            KEY_NO_INTERNET -> return NO_CONNECTION
            KEY_UNAUTHORIZED -> return UNAUTHORIZED
            KEY_SERVER_ERROR -> return SERVER_ERROR
        }
        return SERVER_ERROR
    }
}
