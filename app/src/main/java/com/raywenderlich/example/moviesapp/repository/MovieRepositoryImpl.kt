package com.raywenderlich.example.moviesapp.repository

import com.raywenderlich.example.moviesapp.database.dao.MovieDao
import com.raywenderlich.example.moviesapp.ui.movies.Movie

class MovieRepositoryImpl(
    private val movieDao: MovieDao
): MovieRepository {

    override suspend fun getMovies(): List<Movie> = movieDao.getMovies()

    override suspend fun getMovieById(movieId: String): Movie = movieDao.getMovieById(movieId)

    override suspend fun addMovie(movie: Movie)=  movieDao.addMovie(movie)

    override suspend fun addMovies(movies: List<Movie>) = movieDao.addMovies(movies)

    override suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)


}