package com.example.movies.data.mappers

import com.example.movies.domain.entity.MovieModel
import com.example.movies.domain.entity.MovieModelLocal
import com.example.movies.domain.entity.MovieModelRemote

fun MovieModelLocal.toMovie(): MovieModel {
    return MovieModel(
        this.id, this.name, this.backdropPath, this.posterPath,
        this.adult, this.voteAverage, this.overview, this.releaseDate,
        this.originalLanguage, this.isPopular, this.isTopRated
    )
}

fun MovieModelRemote.toLocalMovie(
    popular: Boolean, topRate: Boolean
): MovieModelLocal {
    return MovieModelLocal(
        id = this.id ?: -1,
        name = this.title ?: "",
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        adult = this.adult ?: false,
        originalLanguage = this.originalLanguage ?: "",
        isPopular = popular,
        isTopRated = topRate
    )
}

fun MovieModelRemote.toMovie(
    popular: Boolean, topRate: Boolean
): MovieModel {
    return MovieModel(
        this.id, this.title, this.backdropPath, this.posterPath,
        this.adult, this.voteAverage, this.overview, this.releaseDate,
        this.originalLanguage, popular, topRate
    )
}

