package com.dev.newsapp.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dev.newsapp.data.model.entity.Article
import com.dev.newsapp.data.model.entity.SourceData

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<Article>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(news: Article)

    @Query("DELETE FROM article")
    suspend fun delete()

    @Query("SELECT * FROM article ORDER BY id ASC")
    fun getAllArticlesAsc(): PagingSource<Int,Article>

    @Query("SELECT * FROM article WHERE title LIKE '%' || :title || '%'")
    suspend fun searchArticle(title: String): List<Article>
}