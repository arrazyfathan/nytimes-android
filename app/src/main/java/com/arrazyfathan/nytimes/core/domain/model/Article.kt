package com.arrazyfathan.nytimes.core.domain.model

data class Article(
    var articleId: String,
    var description: String,
    var byline: String,
    var createdDate: String,
    var itemType: String,
    var kicker: String,
    var materialTypeFacet: String,
    var multimedia: List<Multimedia>,
    var publishedDate: String,
    var section: String,
    var shortUrl: String,
    var subsection: String,
    var title: String,
    var updatedDate: String,
    var uri: String,
    var url: String,
    var isBookmarked: Boolean = false
) {
    fun getImage() = multimedia.last().url
    fun getMainImage() = multimedia.first().url
    fun getImageCaption() = multimedia.map { it.caption }.first()
    fun getImageCopyright() = multimedia.map { it.copyright }.first()
}
