package com.arrazyfathan.nytimes.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.github.marlonlom.utilities.timeago.TimeAgo
import java.time.Instant

class Utils {

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun dateTimeAgo(date: String): String {
            val instant = Instant.parse(date)
            val ms = instant.toEpochMilli()
            return TimeAgo.using(ms).replaceFirstChar { it.uppercaseChar() }
        }
    }
}