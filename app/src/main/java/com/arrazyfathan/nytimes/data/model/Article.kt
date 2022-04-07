package com.arrazyfathan.nytimes.data.model

data class Article(
    val `abstract`: String,
    val byline: String,
    val created_date: String,
    val des_facet: List<String>,
    val item_type: String,
    val material_type_facet: String,
    val multimedia: List<Multimedia>,
    val published_date: String,
    val section: String,
    val short_url: String,
    val subsection: String,
    val title: String,
    val updated_date: String,
    val url: String
)