package com.example.movies.domain.usecase

import androidx.paging.PagingData
import com.example.movies.domain.entity.MovieDetailModel
import com.example.movies.domain.entity.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    suspend fun getMoviesDetail(id: Int): MovieDetailModel

    suspend fun getSearchMovies(query: String): Flow<PagingData<MovieModel>>

    suspend fun getTopRatedMovies(): Flow<PagingData<MovieModel>>

    suspend fun getMostPopularMovies(): Flow<PagingData<MovieModel>>

    suspend fun getUpcomingMovies(): Flow<PagingData<MovieModel>>
}