package com.example.movies.data.remote

import com.example.movies.domain.entity.MovieModelX
import com.example.movies.domain.entity.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("search/movie?api_key=4087a7c0c2602ee5085e12746245fc3f")
    suspend fun searchMovies(
        @Query("query") query: String,
    ): MoviesResponse

    @GET("movie/{custom}?language=en-US&api_key=4087a7c0c2602ee5085e12746245fc3f")
    suspend fun getCustomMovies(
        @Path("custom") custom: String,
        @Query ("page") page: Int,
    ): MoviesResponse


    @GET("movie/{id}?api_key=4087a7c0c2602ee5085e12746245fc3f")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
    ): MovieModelX
}