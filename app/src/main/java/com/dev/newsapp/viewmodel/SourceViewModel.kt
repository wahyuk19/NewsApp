package com.dev.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.newsapp.data.Resource
import com.dev.newsapp.data.model.entity.SourceData
import com.dev.newsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    private val _sources = MutableLiveData<Resource<List<SourceData>>>()

    val sources: LiveData<Resource<List<SourceData>>> get() = _sources
    fun getSources(category: String){
        viewModelScope.launch {
            val result = repository.getSources(category)
            _sources.value = result
        }
    }

    fun searchSource(source: String): MutableLiveData<Resource<List<SourceData>>>{
        loadSource(source)
        return _sources
    }

    private fun loadSource(source: String) {
        viewModelScope.launch {
            val result = repository.searchSource(source)
            _sources.value = result
        }
    }


}