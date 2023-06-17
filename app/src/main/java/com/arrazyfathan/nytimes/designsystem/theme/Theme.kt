package com.arrazyfathan.nytimes.designsystem.theme

import androidx.compose.runtime.Composable
import com.google.accompanist.themeadapter.material.MdcTheme

/**
 * Created by Ar Razy Fathan Rabbani on 15/06/23.
 */

@Composable
fun NyTimesTheme(
    content: @Composable () -> Unit,
) {
    MdcTheme(
        content = content,
    )
}
