package com.dev.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dev.newsapp.data.model.entity.Article
import com.dev.newsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    fun getArticles(sources: String): MutableStateFlow<PagingData<Article>>{
        loadArticle(sources)
        return  _article
    }

    fun searchArticle(title: String): MutableStateFlow<PagingData<Article>>{
        loadArticleByTitle(title)
        return _article
    }

    private val _article: MutableStateFlow<PagingData<Article>> = MutableStateFlow(value = PagingData.empty())

    private fun loadArticleByTitle(title: String){
        viewModelScope.launch {
            getArticleByTitle(title)
        }
    }

    private suspend fun getArticleByTitle(title: String) {
        repository.searchArticle(title).distinctUntilChanged().cachedIn(viewModelScope).collect{
            _article.value = it
        }
    }

    private fun loadArticle(sources: String){
        viewModelScope.launch {
            getArticleBySource(sources)
        }
    }

    private suspend fun getArticleBySource(sources: String){
        repository.getArticles(sources).distinctUntilChanged().cachedIn(viewModelScope).collect{
            _article.value = it
        }
    }
}