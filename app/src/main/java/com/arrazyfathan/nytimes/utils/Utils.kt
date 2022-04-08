package com.arrazyfathan.nytimes.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.github.marlonlom.utilities.timeago.TimeAgo
import java.time.Instant


class Utils {

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun dateTimeAgo(date: String): String {
            val dateFormatted = date.substring(0,19)+"Z"
            val instant = Instant.parse(dateFormatted)
            val ms = instant.toEpochMilli()
            return TimeAgo.using(ms).replaceFirstChar { it.uppercaseChar() }
        }
    }
}