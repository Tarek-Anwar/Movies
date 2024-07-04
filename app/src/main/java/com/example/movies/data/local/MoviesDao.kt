package com.example.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.domain.entity.MovieModelLocal

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getMovies(limit: Int, offset: Int): List<MovieModelLocal>

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieModelLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieModelLocal: MovieModelLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieModelLocal>)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieModelLocal?

    @Query("SELECT * FROM movies WHERE isPopular = 1")
    suspend fun getAllPopularMovies(): List<MovieModelLocal>
}