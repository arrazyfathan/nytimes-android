package com.arrazyfathan.nytimes.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MultimediaDto(
    @SerializedName("caption")
    var caption: String? = "",
    @SerializedName("copyright")
    var copyright: String? = "",
    @SerializedName("format")
    var format: String? = "",
    @SerializedName("height")
    var height: Int? = 0,
    @SerializedName("subtype")
    var subtype: String? = "",
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("url")
    var url: String? = "",
    @SerializedName("width")
    var width: Int? = 0,
)
