package com.example.movies.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieModelLocal(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val backdropPath: String,
    val posterPath: String,
    val adult: Boolean,
    val voteAverage: Double = 0.0,
    val overview: String = "",
    val releaseDate: String = "",
    val originalLanguage: String,
    val type: Int = 0,
    )