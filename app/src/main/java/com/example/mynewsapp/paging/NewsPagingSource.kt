package com.example.mynewsapp.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mynewsapp.models.Article
import com.example.mynewsapp.retrofit.ApiServices
import com.example.mynewsapp.utils.Constants
import java.lang.Exception

/**
 * Handles the pagination, data emission and error handling
 * @param search contains the string to be searched for the user
 */
class NewsPagingSource(
    private val apiServices: ApiServices,
    private val search: String,
    private val errorHandler: MutableLiveData<String>
) :
    PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val pageNumber = params.key ?: 1
            val response =
                if (search.isEmpty()) apiServices.getTopHeadings(page = pageNumber) else apiServices.searchNews(
                    query = search, page = pageNumber
                )
            val list = response.body()?.articles?.filter { it.title != "[Removed]" }
            return if (response.isSuccessful) {
                if (pageNumber == 1 && list.isNullOrEmpty()) {
                    errorHandler.value = Constants.Error.NO_RESULTS
                    LoadResult.Error(Throwable(Constants.Error.NO_RESULTS))
                }
                else LoadResult.Page(
                    data = list ?: emptyList(),
                    prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                    nextKey = if ((response.body()?.articles?.size
                            ?: 0) < 10
                    ) null else pageNumber + 1

                )
            } else {
                errorHandler.value = Constants.Error.SOMETHING_WENT_WRONG
                LoadResult.Error(Throwable(Constants.Error.SOMETHING_WENT_WRONG))
            }
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