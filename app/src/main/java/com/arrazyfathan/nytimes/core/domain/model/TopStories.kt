package com.arrazyfathan.nytimes.core.domain.model

data class TopStories(
    var copyright: String,
    var lastUpdated: String,
    var articles: List<Article>,
    var section: String,
)
