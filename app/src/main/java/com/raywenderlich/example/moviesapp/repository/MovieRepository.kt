package com.raywenderlich.example.moviesapp.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.example.moviesapp.ui.movies.Movie

interface MovieRepository {

    fun getMovies(): LiveData<List<Movie>>

    suspend fun getMovieById(movieId: String): Movie

    suspend fun addMovie(movie: Movie)

    suspend fun addMovies(movies: List<Movie>)

    suspend fun deleteMovie(movie: Movie)
}