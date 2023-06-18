package com.arrazyfathan.nytimes.core.utils

import com.github.marlonlom.utilities.timeago.TimeAgo
import java.text.SimpleDateFormat
import java.util.Locale

class Utils {

    companion object {

        fun dateTimeAgo(date: String): String {
            val dateFormatted = date.substring(0, 19) + "Z"
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val parsedDate = dateFormat.parse(dateFormatted)
            val ms = parsedDate?.time ?: 0
            return TimeAgo.using(ms).replaceFirstChar { it.uppercaseChar() }
        }
    }
}
