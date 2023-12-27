package com.dev.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dev.newsapp.BuildConfig
import com.dev.newsapp.data.Resource
import com.dev.newsapp.data.api.NewsApi
import com.dev.newsapp.data.db.NewsRoomDatabase
import com.dev.newsapp.data.model.Sources
import com.dev.newsapp.data.model.entity.Article
import com.dev.newsapp.data.model.entity.SourceData
import com.dev.newsapp.data.paging.ArticlePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val db: NewsRoomDatabase,
    private val api: NewsApi
) {

    fun getArticles(sources: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                ArticlePagingSource(sources, api)
            }
        ).flow
    }

    suspend fun getSources(category: String): Resource<Sources>{
        val response = try{
            Resource.Loading(data = true)
            api.getSources(category = category)
        }catch (e: Exception){
            return Resource.Error(message = "An error occured ${e.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
    companion object{
        const val PAGE_SIZE = 20
    }
}