package com.arrazyfathan.nytimes.data.model

/**
 * Created by Ar Razy Fathan Rabbani on 01/04/23.
 */
data class Section(
    val name: String,
    var isSelected: Boolean = false,
)

val sectionItems = listOf(
    Section(name = "home", false),
    Section(name = "arts", false),
    Section(name = "automobiles", false),
    Section(name = "books", false),
    Section(name = "business", false),
    Section(name = "fashion", false),
    Section(name = "food", false),
    Section(name = "health", false),
    Section(name = "magazine", false),
    Section(name = "insider", false),
    Section(name = "movies", false),
    Section(name = "science", false),
    Section(name = "politics", false),
    Section(name = "sports", false),
    Section(name = "technology", false),
    Section(name = "travel", false),
    Section(name = "us", false),
    Section(name = "world", false),
)
