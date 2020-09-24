package com.raywenderlich.example.moviesapp.repository

import com.raywenderlich.example.moviesapp.ui.movies.Movie

interface MovieRepository {

    suspend fun getMovies(): List<Movie>

    suspend fun getMovieById(movieId: String): Movie

    suspend fun addMovie(movie: Movie)

    suspend fun addMovies(movies: List<Movie>)

    suspend fun deleteMovie(movie: Movie)
}