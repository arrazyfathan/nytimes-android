package com.arrazyfathan.nytimes.core.data.source.remote.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Created by Ar Razy Fathan Rabbani on 01/04/23.
 */
class NetworkInterceptor(
    private val context: Context,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isInternetAvailable()) {
            return createOfflineResponse(chain)
        }
        return chain.proceed(chain.request())
    }

    private fun createOfflineResponse(chain: Interceptor.Chain): Response {
        val type = "application/json; charset=utf-8".toMediaTypeOrNull()
        return Response
            .Builder()
            .protocol(Protocol.HTTP_1_1)
            .code(KEY_NO_INTERNET)
            .message("No internet connection")
            .body(MessageResult.NO_CONNECTION.toResponseBody(type))
            .request(chain.request())
            .build()
    }
}
