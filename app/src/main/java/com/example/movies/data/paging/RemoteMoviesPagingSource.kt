package com.example.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.local.MoviesDao
import com.example.movies.data.mappers.toLocalMovie
import com.example.movies.data.remote.MoviesApiService
import com.example.movies.domain.entity.MovieModelRemote
import java.util.stream.IntStream.range

class RemoteMoviesPagingSource(
    private val query: String = "",
    private var categoryTypeMovie: String = "popular",
    private var forceCashing: Boolean = true,
    private val moviesApiService: MoviesApiService,
    private val moviesDao: MoviesDao
    ) : PagingSource<Int, MovieModelRemote>() {

    fun setForceCaching(isCache: Boolean) {
        forceCashing = isCache
    }


    override fun getRefreshKey(state: PagingState<Int, MovieModelRemote>): Int? {
       /* return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }*/
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModelRemote> {
        return try {
            val currentPage = params.key ?: 1

            val response = if (query.isNotEmpty()) moviesApiService.searchMovies(query, page = currentPage)
            else moviesApiService.getCustomMovies(categoryTypeMovie, currentPage)

            if (forceCashing && query.isEmpty() && range(1,4).toArray().contains(currentPage)) {
                val localMovies = response.movies.map {
                    it.toLocalMovie()
                }
                moviesDao.insertMovies(localMovies)
            }
            LoadResult.Page(
                data = response.movies,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.movies.isEmpty()) null else currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}