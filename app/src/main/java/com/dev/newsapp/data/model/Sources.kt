package com.dev.newsapp.data.model

import com.dev.newsapp.data.model.entity.SourceData

data class Sources(
    val sources: List<SourceData>,
    val status: String
)