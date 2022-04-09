package com.arrazyfathan.nytimes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var url: String? = "",
    var byline: String? = "",
    var multimedia: List<Multimedia>? = null,
    var published_date: String? = "",
    var section: String? = "",
    var short_url: String? = "",
    var subsection: String? = "",
    @SerializedName("abstract")
    var description: String? = "",
    var title: String? = "",
    var isSaved: Boolean = false
) : Serializable