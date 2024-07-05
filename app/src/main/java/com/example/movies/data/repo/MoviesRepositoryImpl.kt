package com.example.movies.data.repo

import com.example.movies.data.paging.MoviesPagingSourceRemote
import com.example.movies.data.remote.ApiService
import com.example.movies.domain.entity.MovieModelX
import com.example.movies.domain.repo.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : MoviesRepository {
    override fun getSearchMovies(query: String): MoviesPagingSourceRemote =
        MoviesPagingSourceRemote(apiService , query = query)

    override suspend fun getMovieDetails(id: Int): MovieModelX =
        apiService.getMovieDetails(id)

    override  fun getCustomMovies(custom : String): MoviesPagingSourceRemote =
        MoviesPagingSourceRemote(apiService , custom)


}