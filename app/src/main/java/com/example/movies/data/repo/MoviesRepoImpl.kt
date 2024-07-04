package com.example.movies.data.repo

import com.example.movies.data.remote.ApiService
import com.example.movies.domain.entity.MoviesResponse
import com.example.movies.domain.repo.MoviesRepo
import javax.inject.Inject
import javax.inject.Singleton


class MoviesRepoImpl (
    private val apiService: ApiService
) //: MoviesRepo
{
   // override fun getMoviesFromApi(): MoviesResponse = apiService.getPopularMovies()
}