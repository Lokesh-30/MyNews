package com.example.mynewsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.mynewsapp.paging.NewsPagingSource
import com.example.mynewsapp.retrofit.ApiServices
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiServices: ApiServices) {

    fun getNews(search: String) = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        pagingSourceFactory = { NewsPagingSource(apiServices, search) }
    ).liveData
}