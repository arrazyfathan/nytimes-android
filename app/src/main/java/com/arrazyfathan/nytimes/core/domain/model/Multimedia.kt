package com.arrazyfathan.nytimes.core.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Multimedia(
    val caption: String,
    val copyright: String,
    val format: String,
    val height: Int,
    val subtype: String,
    val type: String,
    val url: String,
    val width: Int,
)
