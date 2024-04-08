package com.example.mynewsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mynewsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * View model to hold data for the UI
 */
@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val errorHandler: MutableLiveData<String> = MutableLiveData()
    fun getNews(search: String) = repository.getNews(search) {
        errorHandler.postValue(it)
    }.cachedIn(viewModelScope)
}