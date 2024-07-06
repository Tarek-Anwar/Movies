package com.example.movies.data.remote

import com.example.movies.data.util.API_KEY
import com.example.movies.domain.entity.MovieDetailModel
import com.example.movies.domain.entity.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/{category}?")
    suspend fun getCustomMovies(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MoviesResponse


    @GET("search/movie?")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/{id}?")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailModel

}