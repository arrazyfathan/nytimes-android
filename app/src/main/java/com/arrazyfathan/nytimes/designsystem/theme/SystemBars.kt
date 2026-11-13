package com.arrazyfathan.nytimes.designsystem.theme

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

@Composable
fun SyncStatusBarWithAppBar(color: Color) {
    val view = LocalView.current
    if (view.isInEditMode) return

    SideEffect {
        val activity = view.context.findActivity() ?: return@SideEffect
        val colorInt = color.toArgb()
        val statusBarStyle =
            if (color.luminance() > 0.5f) {
                SystemBarStyle.light(
                    scrim = colorInt,
                    darkScrim = colorInt,
                )
            } else {
                SystemBarStyle.dark(scrim = colorInt)
            }

        activity.enableEdgeToEdge(statusBarStyle = statusBarStyle)
    }
}

private fun Context.findActivity(): ComponentActivity? =
    when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
