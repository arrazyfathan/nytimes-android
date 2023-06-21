package com.arrazyfathan.nytimes.core.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arrazyfathan.nytimes.core.domain.model.Multimedia

/**
 * Created by Ar Razy Fathan Rabbani on 11/04/23.
 */

@Entity("articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    var articleId: String = "",
    var description: String = "",
    var byline: String = "",
    var createdDate: String = "",
    var itemType: String = "",
    var kicker: String = "",
    var materialTypeFacet: String = "",
    var multimedia: List<Multimedia> = emptyList(),
    var publishedDate: String = "",
    var section: String = "",
    var shortUrl: String = "",
    var subsection: String = "",
    var title: String = "",
    var updatedDate: String = "",
    var uri: String = "",
    var url: String = "",
    var isBookmarked: Boolean = false,
)
