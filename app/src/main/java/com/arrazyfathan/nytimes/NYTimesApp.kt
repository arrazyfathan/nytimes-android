package com.arrazyfathan.nytimes

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.arrazyfathan.logging.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NYTimesApp : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        Logger.init()
    }
}
