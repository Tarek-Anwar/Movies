package com.example.movies.domain.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Keep
@Entity(tableName = "movies")
data class MovieModel(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @PrimaryKey val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
){
    @Ignore
    val video: Boolean? = null
    @Ignore
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null
}