package com.example.movies.domain.repo

import com.example.movies.data.paging.MoviesPagingSourceRemote
import com.example.movies.domain.entity.MovieModelX

interface MoviesRepository {

    fun getSearchMovies(query : String): MoviesPagingSourceRemote

    suspend fun getMovieDetails(id: Int): MovieModelX

    fun getCustomMovies(custom: String): MoviesPagingSourceRemote
}