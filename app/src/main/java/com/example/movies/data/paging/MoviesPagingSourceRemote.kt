package com.example.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.remote.ApiService
import com.example.movies.domain.entity.MovieModelRemote

class MoviesPagingSourceRemote(
    private val apiService: ApiService,
    private val customTypeMovie: String = "popular",
    private val query: String = ""
) : PagingSource<Int, MovieModelRemote>() {


    override fun getRefreshKey(state: PagingState<Int, MovieModelRemote>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModelRemote> {
        return try {
            val currentPage = params.key ?: 1

            val response = if (query.isNotEmpty()) apiService.searchMovies(query)
            else apiService.getCustomMovies(customTypeMovie,currentPage)

            LoadResult.Page(
                data = response.movies,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.movies.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}