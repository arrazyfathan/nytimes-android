package com.arrazyfathan.nytimes.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.arrazyfathan.nytimes.R
import com.arrazyfathan.nytimes.designsystem.theme.NyTimesTheme
import com.arrazyfathan.nytimes.presentation.navigation.NyTimesNavDisplay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NYTimes)
        enableEdgeToEdge()
        setContent {
            NyTimesTheme {
                NyTimesNavDisplay()
            }
        }
    }
}
