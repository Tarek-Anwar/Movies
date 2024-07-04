package com.example.movies.domain.usecase

import com.example.movies.data.repo.MoviesRepoImpl
import com.example.movies.domain.entity.MovieModel

class GetMoviesPopularUserCase() {
    private val repo = MoviesRepoImpl()
    suspend operator fun invoke(): List<MovieModel> {
        return repo.getAllPopularMovies()
    }
}