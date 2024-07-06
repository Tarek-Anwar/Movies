package com.example.movies.domain.entity

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val dates: Any? = null,
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieModelRemote>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,

)