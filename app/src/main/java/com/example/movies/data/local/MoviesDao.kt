package com.example.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.domain.entity.MovieModelLocal

@Dao
interface MoviesDao {

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

}