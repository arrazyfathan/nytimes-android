package com.arrazyfathan.nytimes.core.data.source.remote.network

import com.arrazyfathan.logging.Logger.d
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by Ar Razy Fathan Rabbani on 01/04/23.
 */
class LoggingInterceptor : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson = GsonBuilder().setPrettyPrinting()
                    .create().toJson(JsonParser().parse(message))
                d("ApiLogger : $prettyPrintJson")
            } catch (m: JsonSyntaxException) {
                d("ApiLogger : $message")
            }
        } else {
            d("ApiLogger : $message")
            return
        }
    }
}
