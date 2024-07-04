package com.example.movies.domain.entity

data class MovieModel(
    val id: Int? = null,
    val name: String,
    val backdropPath: String,
    val posterPath: String,
    val adult: Boolean,
    val voteAverage: Double = 0.0,
    val overview: String = "",
    val releaseDate: String = "",
    val originalLanguage: String,
    val isPopular: Boolean,
    val isTopRated: Boolean,
)