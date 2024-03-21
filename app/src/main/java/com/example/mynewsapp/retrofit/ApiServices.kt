package com.example.mynewsapp.retrofit

import com.example.mynewsapp.models.NewsResponse
import com.example.mynewsapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET(Constants.ServerUrls.TOP_HEADINGS)
    suspend fun getTopHeadings(
        @Query("country") country: String = "in"
    ): Response<NewsResponse>

    @GET(Constants.ServerUrls.EVERYTHING)
    suspend fun searchNews(
        @Query("q") query: String
    ): Response<NewsResponse>

}