package com.example.movies.domain.repo

import com.example.movies.data.paging.RemoteMoviesPagingSource
import com.example.movies.domain.entity.MovieDetailModel

interface MoviesRepository {

    fun getSearchMovies(query : String): RemoteMoviesPagingSource

    suspend fun getMovieDetails(id: Int): MovieDetailModel

    fun getCustomMovies(custom: String): RemoteMoviesPagingSource
}