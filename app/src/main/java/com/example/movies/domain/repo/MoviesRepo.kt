package com.example.movies.domain.repo

import com.example.movies.domain.entity.MoviesResponse

interface MoviesRepo {
    suspend fun getMoviesFromApi(): MoviesResponse
}