package com.dev.newsapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dev.newsapp.BuildConfig
import com.dev.newsapp.data.Resource
import com.dev.newsapp.data.api.NewsApi
import com.dev.newsapp.data.db.ArticleRemoteMediator
import com.dev.newsapp.data.db.NewsRoomDatabase
import com.dev.newsapp.data.model.Sources
import com.dev.newsapp.data.model.entity.Article
import com.dev.newsapp.data.model.entity.SourceData
import com.dev.newsapp.data.paging.ArticlePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepository @Inject constructor(
    private val db: NewsRoomDatabase,
    private val api: NewsApi
) {

    fun getArticles(sources: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            remoteMediator = ArticleRemoteMediator(sources,db,api),
            pagingSourceFactory = {
                db.articleDao().getAllArticlesAsc()
            }
        ).flow
    }

    fun searchArticle(source: String): Flow<PagingData<Article>>{
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            remoteMediator = ArticleRemoteMediator(source, db, api),
            pagingSourceFactory = {
                db.articleDao().getAllArticlesAsc()
            }
        ).flow
    }

    suspend fun getSources(category: String): Resource<List<SourceData>>{
        withContext(Dispatchers.IO){
            db.sourceDao().delete()
        }

        val response = try{
            Resource.Loading(data = true)
            api.getSources(category = category).sources
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Error(message = "An error occured ${e.message.toString()}")
        }
        Resource.Loading(data = false)

        withContext(Dispatchers.IO){
            db.sourceDao().insert(response)
        }
        return Resource.Success(data = withContext(Dispatchers.IO){
            db.sourceDao().getAllSourcesAsc()
        })
    }

    suspend fun searchSource(source: String): Resource<List<SourceData>>{
        Resource.Loading(data = true)
        val data = withContext(Dispatchers.IO){
            db.sourceDao().searchSource(source)
        }
        Resource.Loading(data = false)

        return Resource.Success(data = data)
    }
    companion object{
        const val PAGE_SIZE = 20
    }
}