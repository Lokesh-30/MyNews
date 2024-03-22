package com.example.mynewsapp.retrofit

import com.example.mynewsapp.models.NewsResponse
import com.example.mynewsapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    /**
     * Api used to get the Top Headings
     */
    @GET(Constants.ServerUrls.TOP_HEADINGS)
    suspend fun getTopHeadings(
        @Query("country") country: String = "in",
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int,
    ): Response<NewsResponse>

    /**
     * Api used to search through the News
     * @param query It is the string to be searched for
     */
    @GET(Constants.ServerUrls.EVERYTHING)
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int,
    ): Response<NewsResponse>

}