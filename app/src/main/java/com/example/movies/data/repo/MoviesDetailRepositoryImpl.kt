package com.example.movies.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movies.data.local.MoviesDao
import com.example.movies.data.paging.LocalMoviesPagingSource
import com.example.movies.data.paging.MoviesType
import com.example.movies.data.paging.RemoteMoviesPagingSource
import com.example.movies.data.remote.MoviesApiService
import com.example.movies.domain.entity.MovieDetailModel
import com.example.movies.domain.entity.MovieModel
import com.example.movies.domain.entity.toMovieModel
import com.example.movies.domain.repo.MoviesDetailRepository
import com.example.movies.domain.repo.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesDetailRepositoryImpl @Inject constructor(
     private val moviesApiService: MoviesApiService,
    private val moviesDao: MoviesDao,
) : MoviesDetailRepository {

    override suspend fun getMovieDetailsRemote(id: Int): MovieDetailModel = moviesApiService.getMovieDetails(id)

    override suspend fun getMovieDetailsLocal(id: Int): MovieDetailModel = moviesDao.getMovieById(id)!!.toMovieModel()

}