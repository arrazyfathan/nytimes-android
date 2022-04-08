package com.arrazyfathan.nytimes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arrazyfathan.nytimes.data.model.Article

@Database(
    entities = [Article::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {

        @Volatile
        private var INSTACE: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase {
            return INSTACE ?: synchronized(this) {
                val instace = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "article_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTACE = instace
                instace
            }
        }
    }
}