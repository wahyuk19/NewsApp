package com.dev.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dev.newsapp.data.model.entity.Article
import com.dev.newsapp.data.model.entity.RemoteKeys
import com.dev.newsapp.data.model.entity.SourceData
import com.dev.newsapp.utils.SourceConverters

@Database(entities = [Article::class,RemoteKeys::class,SourceData::class], version = 1, exportSchema = false)
@TypeConverters(SourceConverters::class)
abstract class NewsRoomDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun sourceDao(): SourceDao
}