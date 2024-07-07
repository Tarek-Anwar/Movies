package com.example.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.local.MoviesDao
import com.example.movies.domain.entity.MovieModel
import java.io.IOException


class LocalMoviesPagingSource(
    private val moviesDao: MoviesDao,
    private val firstPage: Int = 1,
) : PagingSource<Int, MovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
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
                prevKey = if (currentPage == 1) null else currentPage -1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}