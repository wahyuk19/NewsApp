package com.dev.newsapp.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.newsapp.data.model.Source

@Entity(tableName = "article", indices = [Index(value = ["id"],unique = true)])
data class Article(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "avatar_url")
    val author: String?,

    @ColumnInfo(name = "content")
    val content: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String?,

    @ColumnInfo(name = "source")
    val source: Source,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String?
)