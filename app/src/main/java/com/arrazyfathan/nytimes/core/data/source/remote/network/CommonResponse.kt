package com.arrazyfathan.nytimes.core.data.source.remote.network

import com.google.gson.annotations.SerializedName

/**
 * Created by Ar Razy Fathan Rabbani on 01/04/23.
 */
open class CommonResponse(
    @SerializedName("statusCode")
    val statusCode: Int = 500,
    @SerializedName("message")
    var message: String? = "Server Error",
    @SerializedName("error")
    val error: MutableList<String>? = ArrayList()
)