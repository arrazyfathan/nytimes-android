package com.arrazyfathan.nytimes.data.model

import java.io.Serializable

data class Article(
    val abstract: String,
    val byline: String,
    val multimedia: List<Multimedia>,
    val published_date: String,
    val section: String,
    val short_url: String,
    val subsection: String,
    val title: String,
    val url: String
) : Serializable