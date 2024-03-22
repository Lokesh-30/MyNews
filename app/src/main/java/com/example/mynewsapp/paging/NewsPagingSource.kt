package com.example.mynewsapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mynewsapp.models.Article
import com.example.mynewsapp.retrofit.ApiServices
import com.example.mynewsapp.utils.Constants
import java.lang.Exception

class NewsPagingSource(private val apiServices: ApiServices, private val search: String) :
    PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val pageNumber = params.key ?: 1
            val response =
                if (search.isEmpty()) apiServices.getTopHeadings(page = pageNumber) else apiServices.searchNews(
                    query = search, page = pageNumber
                )
            return if (response.isSuccessful) LoadResult.Page(
                data = response.body()?.articles ?: emptyList(),
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if ((response.body()?.articles?.size
                        ?: 0) < 10
                ) null else pageNumber + 1
            ) else LoadResult.Error(Throwable(Constants.Error.SOMETHING_WENT_WRONG))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}