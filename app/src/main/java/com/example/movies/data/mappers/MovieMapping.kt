package com.example.movies.data.mappers

import com.example.movies.domain.entity.MovieModelLocal
import com.example.movies.domain.entity.MovieModelRemote


fun MovieModelRemote.toLocalMovie( type : Int = 0): MovieModelLocal {
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
        type = type ?: 0
    )
}

