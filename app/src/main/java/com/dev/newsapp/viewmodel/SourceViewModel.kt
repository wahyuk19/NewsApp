package com.dev.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.dev.newsapp.data.Resource
import com.dev.newsapp.data.model.Sources
import com.dev.newsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    private val _sources = MutableLiveData<Resource<Sources>>()

    val sources: LiveData<Resource<Sources>> get() = _sources
    fun getSources(category: String){
        viewModelScope.launch {
            val result = repository.getSources(category)
            _sources.value = result
        }
    }

}