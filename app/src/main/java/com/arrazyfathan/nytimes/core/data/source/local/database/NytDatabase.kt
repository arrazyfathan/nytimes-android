package com.arrazyfathan.nytimes.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arrazyfathan.nytimes.core.data.source.local.entities.ArticleEntity

/**
 * Created by Ar Razy Fathan Rabbani on 11/04/23.
 */

@Database(entities = [ArticleEntity::class], exportSchema = false, version = 4)
@TypeConverters(Converters::class)
abstract class NytDatabase : RoomDatabase() {
    abstract fun nytDao(): NytDao
}
