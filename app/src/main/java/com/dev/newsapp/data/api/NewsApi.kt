package com.dev.newsapp.data.api

import com.dev.newsapp.data.model.News
import com.dev.newsapp.data.model.Sources
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getArticles(

    ):News

    @GET("top-headlines")
    suspend fun getHeadlines(

    ):News

    @GET("top-headlines/sources")
    suspend fun getSources(

    ):Sources
}