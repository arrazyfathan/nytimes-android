package com.arrazyfathan.nytimes.core.data.source.local.entities

import androidx.room.Entity
import com.arrazyfathan.nytimes.core.domain.model.Multimedia

@Entity(
    tableName = "cached_articles",
    primaryKeys = ["querySection", "uri"],
)
data class CachedArticleEntity(
    val querySection: String,
    val uri: String,
    val position: Int,
    val articleId: String = "",
    val description: String = "",
    val byline: String = "",
    val createdDate: String = "",
    val itemType: String = "",
    val kicker: String = "",
    val materialTypeFacet: String = "",
    val multimedia: List<Multimedia> = emptyList(),
    val publishedDate: String = "",
    val section: String = "",
    val shortUrl: String = "",
    val subsection: String = "",
    val title: String = "",
    val updatedDate: String = "",
    val url: String = "",
)
