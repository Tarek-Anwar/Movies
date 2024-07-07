package com.example.movies.domain.repo

import androidx.paging.Pager
import com.example.movies.data.paging.RemoteMoviesPagingSource
import com.example.movies.domain.entity.MovieDetailModel
import com.example.movies.domain.entity.MovieModel

interface MoviesRepository {

    suspend fun getSearchMovies(query: String): Pager<Int, MovieModel>

    suspend fun getPopularMovies(): Pager<Int, MovieModel>

    suspend fun getTopRatedMovies(): Pager<Int, MovieModel>

    suspend fun getUpcomingMovies(): Pager<Int, MovieModel>

    suspend fun getCachedMovies(): Pager<Int, MovieModel>


}