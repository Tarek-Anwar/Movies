package com.example.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.local.MoviesDao
import com.example.movies.data.remote.MoviesApiService
import com.example.movies.domain.entity.MovieModel

enum class MoviesType {
    POPULAR,
    TOP_RATED,
    UPCOMING,
    NONE
}

class RemoteMoviesPagingSource(
    private val apiService: MoviesApiService,
    private val moviesDao: MoviesDao,
    private val firstPage: Int = 1,
    private var moviesType: MoviesType = MoviesType.NONE,
    private var query: String = "",
) : PagingSource<Int, MovieModel>() {


    fun setMoviesType(moviesType: MoviesType) {
        this.moviesType = moviesType
    }

    fun setQuery(query: String) {
        this.query = query
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val currentPage = params.key ?: firstPage
            val moviesList = if (query.isEmpty()) {
                when (moviesType) {
                    MoviesType.POPULAR -> apiService.getMoviesWithCategory(
                        "popular",
                        currentPage
                    ).movies

                    MoviesType.TOP_RATED -> apiService.getMoviesWithCategory(
                        "top_rated",
                        currentPage
                    ).movies

                    MoviesType.UPCOMING -> apiService.getMoviesWithCategory(
                        "upcoming",
                        currentPage
                    ).movies

                    MoviesType.NONE -> {
                        throw IllegalArgumentException(
                            "Parameter MoviesType Not passed",
                            Throwable("Please select movies type to get")
                        )
                    }
                }
            } else {
                apiService.searchMovies(currentPage, query = query).movies
            }

           // moviesDao.insertMovies(moviesList)
            val nextPage: Int? =
                if (moviesList.isEmpty()) null else currentPage.plus(1)
            LoadResult.Page(
                data = moviesList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private val TAG = "RemoteMoviesPagingSourc"
}