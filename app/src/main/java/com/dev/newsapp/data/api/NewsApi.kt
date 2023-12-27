package com.dev.newsapp.data.api

import com.dev.newsapp.BuildConfig
import com.dev.newsapp.data.model.News
import com.dev.newsapp.data.model.Sources
import retrofit2.http.GET
import retrofit2.http.Query

val key = BuildConfig.API_KEY
interface NewsApi {

    @GET("everything")
    suspend fun getArticles(

    ):News

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String? = key,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ):News

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String? = key
    ):Sources
}