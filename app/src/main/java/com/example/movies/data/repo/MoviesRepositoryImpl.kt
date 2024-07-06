package com.example.movies.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.data.local.MoviesDao
import com.example.movies.data.paging.RemoteMoviesPagingSource
import com.example.movies.data.remote.MoviesApiService
import com.example.movies.domain.entity.MovieDetailModel
import com.example.movies.domain.entity.MovieModelRemote
import com.example.movies.domain.repo.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApiService: MoviesApiService,
    private val dao: MoviesDao,
) : MoviesRepository {
    override fun getSearchMovies(query: String): RemoteMoviesPagingSource =
        RemoteMoviesPagingSource(
            query = query,
            moviesApiService = moviesApiService,
            moviesDao = dao
        )

    override suspend fun getMovieDetails(id: Int): MovieDetailModel =
        moviesApiService.getMovieDetails(id)

    override fun getCustomMovies(custom: String): RemoteMoviesPagingSource =
        RemoteMoviesPagingSource(
            categoryTypeMovie = custom,
            moviesApiService = moviesApiService,
            moviesDao = dao
        )


}