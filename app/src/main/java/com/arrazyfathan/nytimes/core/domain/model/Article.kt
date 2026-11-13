package com.arrazyfathan.nytimes.core.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Article(
    val articleId: String,
    val description: String,
    val byline: String,
    val createdDate: String,
    val itemType: String,
    val kicker: String,
    val materialTypeFacet: String,
    val multimedia: List<Multimedia>,
    val publishedDate: String,
    val section: String,
    val shortUrl: String,
    val subsection: String,
    val title: String,
    val updatedDate: String,
    val uri: String,
    val url: String,
    val isBookmarked: Boolean = false,
) {
    fun getImage(): String = multimedia.lastOrNull()?.url.orEmpty()

    fun getMainImage(): String = multimedia.firstOrNull()?.url.orEmpty()

    fun getImageCaption(): String = multimedia.firstOrNull()?.caption.orEmpty()

    fun getImageCopyright(): String = multimedia.firstOrNull()?.copyright.orEmpty()
}
