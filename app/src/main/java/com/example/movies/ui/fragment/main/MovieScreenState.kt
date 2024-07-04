package com.example.movies.ui.fragment.main

import com.example.movies.domain.entity.MovieModel

data class MovieScreenState(
    val moviesList: List<MovieModel>?,
    val isLoading: Boolean,
    val error: String? = null
)
