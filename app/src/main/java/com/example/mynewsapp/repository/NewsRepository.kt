package com.example.mynewsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.mynewsapp.paging.NewsPagingSource
import com.example.mynewsapp.retrofit.ApiServices
import javax.inject.Inject

/**
 * Repository handles the data given by the data source
 * @param apiServices Contains the API's to get the data from Server
 */
class NewsRepository @Inject constructor(private val apiServices: ApiServices) {

    fun getNews(search: String, errorHandler: (String) -> Unit) = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 6),
        pagingSourceFactory = {
            NewsPagingSource(apiServices, search, errorHandler)
        }
    ).liveData
}