package com.example.movies.data.repo

import com.example.movies.MovieApp
import com.example.movies.data.local.MovieDataBase
import com.example.movies.data.remote.ApiService
import com.example.movies.domain.entity.MovieModel
import com.example.movies.domain.entity.MovieModelLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoviesRepoImpl(
) //: MoviesRepo
{
    // override fun getMoviesFromApi(): MoviesResponse = apiService.getPopularMovies()
    private var apiService = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)

    private var moviesDao = MovieDataBase.getDaoInstance(MovieApp.getApplication())

    private suspend fun getPopularMovies() = withContext(Dispatchers.IO) {
        try {
            val movies = apiService.getPopularMovies()
            return@withContext movies
        } catch (ex: Exception) {
            throw Exception("check your internet connection")
        }
    }

    private suspend fun updateDataBase() {
        val movies = getPopularMovies()
        moviesDao.insertMovies(movies.movies.map { movie ->
            MovieModelLocal(
                id = movie.id,
                name = movie.title,
                posterPath = movie.posterPath,
                backdropPath = movie.backdropPath,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                voteAverage = movie.voteAverage,
                adult = movie.adult,
                originalLanguage = movie.originalLanguage,
                isPopular = true,
                isTopRated = false
            )
        })
    }

    suspend fun getAllPopularMovies() = withContext(Dispatchers.IO) {
        updateDataBase()
        return@withContext moviesDao.getAllPopularMovies().map {
            MovieModel(
                it.id, it.name, it.backdropPath, it.posterPath,
                it.adult, it.voteAverage, it.overview, it.releaseDate,
                it.originalLanguage, it.isPopular, it.isTopRated
            )
        }
    }

}