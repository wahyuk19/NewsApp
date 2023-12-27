package com.dev.newsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dev.newsapp.data.model.entity.SourceData

@Dao
interface SourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<SourceData>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(news: SourceData)

    @Query("DELETE FROM source")
    suspend fun delete()

    @Query("SELECT * FROM source ORDER BY id ASC")
    fun getAllSourcesAsc(): List<SourceData>
}