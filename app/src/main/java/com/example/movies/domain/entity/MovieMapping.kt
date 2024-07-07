package com.example.movies.domain.entity

fun MovieModel.toMovieModel(): MovieDetailModel {
    return MovieDetailModel(
        this.adult ?: false,
        this.backdropPath ?: "",
        this.id ?: -1,
        originalLanguage =this.originalLanguage ?: "",
        originalTitle = this.originalTitle ?: "",
        overview = this.overview ?: "",
        popularity = this.popularity ?: 0.0,
        posterPath = this.posterPath ?: "",
        releaseDate = this.releaseDate ?: "",
        title = this.title ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        voteCount = this.voteCount ?: 0
    )

}