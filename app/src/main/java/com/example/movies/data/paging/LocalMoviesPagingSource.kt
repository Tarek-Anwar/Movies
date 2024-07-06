package com.example.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.local.MoviesDao
import com.example.movies.domain.entity.MovieModelLocal
import java.io.IOException


class LocalMoviesPagingSource(
    private val moviesDao: MoviesDao,
    private val firstPage: Int = 1,
) : PagingSource<Int, MovieModelLocal>() {
    override fun getRefreshKey(state: PagingState<Int, MovieModelLocal>): Int? {
        return 0;
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModelLocal> {
        return try {
            val currentPage = params.key ?: firstPage

            val moviesList = moviesDao.getMovies(
                params.loadSize,
                (currentPage - 1) * params.loadSize
            )

            val nextPage: Int? =
                if (moviesList.isEmpty()) null else currentPage.plus(1)

            if (moviesList.isEmpty()) {
                throw IOException()
            }

            LoadResult.Page(
                data = moviesList,
                prevKey = if (currentPage == 1) null else currentPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}