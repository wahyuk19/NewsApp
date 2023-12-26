package com.dev.newsapp.data.model

import com.dev.newsapp.data.model.entity.Article

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)