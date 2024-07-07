package com.example.movies.domain.repo

import com.example.movies.domain.entity.MovieDetailModel
import com.example.movies.domain.entity.MovieModel

interface MoviesDetailRepository {

    suspend fun getMovieDetailsRemote(id: Int): MovieDetailModel

    suspend fun getMovieDetailsLocal(id: Int): MovieDetailModel
}