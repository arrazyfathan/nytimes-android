package com.arrazyfathan.nytimes.core.utils

import com.arrazyfathan.nytimes.core.data.source.local.entities.ArticleEntity
import com.arrazyfathan.nytimes.core.data.source.remote.response.ArticleDto
import com.arrazyfathan.nytimes.core.data.source.remote.response.MultimediaDto
import com.arrazyfathan.nytimes.core.data.source.remote.response.TopStoriesDto
import com.arrazyfathan.nytimes.core.domain.model.Article
import com.arrazyfathan.nytimes.core.domain.model.Multimedia
import com.arrazyfathan.nytimes.core.domain.model.TopStories

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */

fun TopStoriesDto.mapToDomain(): TopStories {
    return TopStories(
        copyright = this.copyright.orEmpty(),
        lastUpdated = this.lastUpdated.orEmpty(),
        articles = this.articleDtos.map { it.mapToDomain() },
        section = this.section.orEmpty(),
    )
}

fun ArticleDto.mapToDomain(): Article {
    return Article(
        articleId = "",
        description = this.description.orEmpty(),
        byline = this.byline.orEmpty(),
        createdDate = this.createdDate.orEmpty(),
        itemType = this.itemType.orEmpty(),
        kicker = this.kicker.orEmpty(),
        materialTypeFacet = this.materialTypeFacet.orEmpty(),
        multimedia = this.multimediaDto?.map { it.mapToDomain() } ?: emptyList(),
        publishedDate = this.publishedDate.orEmpty(),
        section = this.section.orEmpty(),
        shortUrl = this.shortUrl.orEmpty(),
        subsection = this.subsection.orEmpty(),
        title = this.title.orEmpty(),
        updatedDate = this.updatedDate.orEmpty(),
        uri = this.uri.orEmpty(),
        url = this.url.orEmpty(),
    )
}

fun MultimediaDto.mapToDomain(): Multimedia {
    return Multimedia(
        caption = this.caption.orEmpty(),
        copyright = this.copyright.orEmpty(),
        format = this.format.orEmpty(),
        height = this.height ?: 0,
        subtype = this.subtype.orEmpty(),
        type = this.type.orEmpty(),
        url = this.url.orEmpty(),
        width = this.width ?: 0,
    )
}

fun ArticleEntity.mapToDomain(): Article {
    return Article(
        articleId = this.articleId,
        description = this.description,
        byline = this.byline,
        createdDate = this.createdDate,
        itemType = this.itemType,
        kicker = this.kicker,
        materialTypeFacet = this.materialTypeFacet,
        multimedia = this.multimedia,
        publishedDate = this.publishedDate,
        section = this.section,
        shortUrl = this.shortUrl,
        subsection = this.subsection,
        title = this.title,
        updatedDate = this.updatedDate,
        uri = this.uri,
        url = this.url,
    )
}

fun Article.mapToEntity(): ArticleEntity {
    return ArticleEntity(
        articleId = this.articleId,
        description = this.description,
        byline = this.byline,
        createdDate = this.createdDate,
        itemType = this.itemType,
        kicker = this.kicker,
        materialTypeFacet = this.materialTypeFacet,
        multimedia = this.multimedia,
        publishedDate = this.publishedDate,
        section = this.section,
        shortUrl = this.shortUrl,
        subsection = this.subsection,
        title = this.title,
        updatedDate = this.updatedDate,
        uri = this.uri,
        url = this.url,
    )
}
