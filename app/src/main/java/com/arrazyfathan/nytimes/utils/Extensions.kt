package com.arrazyfathan.nytimes.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Ar Razy Fathan Rabbani on 11/04/23.
 */

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    return LocalDateTime.parse(this, formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.format(pattern: String, locale: Locale = Locale.getDefault()): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, locale)
    return this.format(formatter)
}

fun Context.launchUrl(url: String) {
    val builder = CustomTabsIntent.Builder()
        .setInitialActivityHeightPx(200)
        .setCloseButtonPosition(CustomTabsIntent.CLOSE_BUTTON_POSITION_END)
        .build()

    builder.launchUrl(this, Uri.parse(url))
}

inline fun <reified T> fromJson(json: String): T {
    return Gson().fromJson(json, object : TypeToken<T>() {}.type)
}

fun Any.toJson(): String {
    return Gson().toJson(this)
}
