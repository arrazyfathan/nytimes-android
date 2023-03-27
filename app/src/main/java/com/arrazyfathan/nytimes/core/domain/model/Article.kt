package com.arrazyfathan.nytimes.core.domain.model

data class Article(
    var `abstract`: String,
    var byline: String,
    var createdDate: String,
    var desFacet: List<String>? = listOf(),
    var geoFacet: List<String>? = listOf(),
    var itemType: String,
    var kicker: String,
    var materialTypeFacet: String,
    var multimedia: List<Multimedia>,
    var orgFacet: List<String>,
    var perFacet: List<String>,
    var publishedDate: String,
    var section: String,
    var shortUrl: String,
    var subsection: String,
    var title: String,
    var updatedDate: String,
    var uri: String,
    var url: String,
)
