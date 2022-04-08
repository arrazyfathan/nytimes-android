package com.arrazyfathan.nytimes.data.model

data class TopStories(
    val results: List<Article>,
    val num_result: Int,
    val status: String

)