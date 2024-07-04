package com.example.movies.domain.usecase

import com.example.movies.domain.repo.MoviesRepo
import javax.inject.Inject

class GetMovies @Inject constructor( private val moviesRepo: MoviesRepo) {
    suspend operator fun invoke() = moviesRepo.getMoviesFromApi()
}