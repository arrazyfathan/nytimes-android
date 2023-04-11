package com.arrazyfathan.nytimes.core.di

import android.content.Context
import androidx.room.Room
import com.arrazyfathan.nytimes.core.data.source.local.database.NytDao
import com.arrazyfathan.nytimes.core.data.source.local.database.NytDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ar Razy Fathan Rabbani on 11/04/23.
 */

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NytDatabase = Room.databaseBuilder(
        context,
        NytDatabase::class.java,
        "nytimes.db",
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideNtyDao(database: NytDatabase): NytDao = database.nytDao()
}
