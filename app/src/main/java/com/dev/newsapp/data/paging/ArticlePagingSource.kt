package com.dev.newsapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dev.newsapp.data.api.NewsApi
import com.dev.newsapp.data.model.entity.Article
import com.dev.newsapp.data.repository.NewsRepository.Companion.PAGE_SIZE

class ArticlePagingSource(private val sources: String,private val api: NewsApi): PagingSource<Int, Article>(){
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = api.getHeadlines(sources = sources, pageSize = PAGE_SIZE, page = position)

            LoadResult.Page(
                data = responseData.articles,
                prevKey = if(position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if(responseData.articles.isEmpty()) null else position +1
            )
        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }
}