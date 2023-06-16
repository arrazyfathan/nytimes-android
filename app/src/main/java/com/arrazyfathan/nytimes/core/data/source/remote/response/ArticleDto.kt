package com.arrazyfathan.nytimes.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleDto(
    @SerializedName("abstract")
    var description: String? = "",
    @SerializedName("byline")
    var byline: String? = "",
    @SerializedName("created_date")
    var createdDate: String? = "",
    @SerializedName("item_type")
    var itemType: String? = "",
    @SerializedName("kicker")
    var kicker: String? = "",
    @SerializedName("material_type_facet")
    var materialTypeFacet: String? = "",
    @SerializedName("multimedia")
    var multimediaDto: List<MultimediaDto>? = listOf(),
    @SerializedName("published_date")
    var publishedDate: String? = "",
    @SerializedName("section")
    var section: String? = "",
    @SerializedName("short_url")
    var shortUrl: String? = "",
    @SerializedName("subsection")
    var subsection: String? = "",
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("updated_date")
    var updatedDate: String? = "",
    @SerializedName("uri")
    var uri: String? = "",
    @SerializedName("url")
    var url: String? = "",
)
