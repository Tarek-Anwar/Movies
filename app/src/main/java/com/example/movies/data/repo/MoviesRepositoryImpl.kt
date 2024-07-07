package com.example.movies.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.data.paging.LocalMoviesPagingSource
import com.example.movies.data.paging.MoviesType
import com.example.movies.data.paging.RemoteMoviesPagingSource
import com.example.movies.domain.entity.MovieModel
import com.example.movies.domain.repo.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesLocalPagingSource: LocalMoviesPagingSource,
    private val remoteMoviesPagingSource: RemoteMoviesPagingSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

) : MoviesRepository {
    override suspend fun getSearchMovies(query: String): Pager<Int, MovieModel> =
        withContext(dispatcher) {
            remoteMoviesPagingSource.setQuery(query)
            return@withContext Pager(config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                remoteMoviesPagingSource
            })
        }


    override suspend fun getPopularMovies(): Pager<Int, MovieModel> =
        withContext(dispatcher) {
            remoteMoviesPagingSource.setMoviesType(MoviesType.POPULAR)
            return@withContext Pager(config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                remoteMoviesPagingSource
            })
        }

    override suspend fun getTopRatedMovies(): Pager<Int, MovieModel> =
        withContext(dispatcher) {
            remoteMoviesPagingSource.setMoviesType(MoviesType.TOP_RATED)
            return@withContext Pager(config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                remoteMoviesPagingSource
            })
        }

    override suspend fun getUpcomingMovies(): Pager<Int, MovieModel> =
        withContext(dispatcher) {
            remoteMoviesPagingSource.setMoviesType(MoviesType.UPCOMING)
            return@withContext Pager(config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                remoteMoviesPagingSource
            })
        }

    override suspend fun getCachedMovies(): Pager<Int, MovieModel> =
        withContext(dispatcher) {
            return@withContext Pager(config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                moviesLocalPagingSource
            })
        }


}