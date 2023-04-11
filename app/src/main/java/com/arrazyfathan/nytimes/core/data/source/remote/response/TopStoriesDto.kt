package com.arrazyfathan.nytimes.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TopStoriesDto(
    @SerializedName("copyright")
    var copyright: String? = "",
    @SerializedName("last_updated")
    var lastUpdated: String? = "",
    @SerializedName("num_results")
    var numResults: Int? = 0,
    @SerializedName("results")
    var articleDtos: List<ArticleDto> = listOf(),
    @SerializedName("section")
    var section: String? = "",
    @SerializedName("status")
    var status: String? = "",
)
