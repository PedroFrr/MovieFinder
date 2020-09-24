package com.raywenderlich.example.moviesapp.database.dao

import androidx.room.*
import com.raywenderlich.example.moviesapp.ui.movies.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM  Movie")
    suspend fun getMovies(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    suspend fun getMovieById(movieId: String): Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<Movie>)

    @Delete
    suspend fun deleteMovie(movie: Movie)

}