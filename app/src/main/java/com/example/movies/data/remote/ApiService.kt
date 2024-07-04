package com.example.movies.data.remote

import com.example.movies.domain.entity.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular?language=en-US&page=1&api_key=4087a7c0c2602ee5085e12746245fc3f")
    suspend fun getPopularMovies(): MoviesResponse

}