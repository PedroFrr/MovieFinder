package com.raywenderlich.example.moviesapp.ui.movie_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.ui.movies.Movie

//TODO implement ViewModel - see the benefits, study this topic
class MovieViewModel(application: Application): AndroidViewModel(application) {

    private val repository by lazy { App.movieRepository}

    fun getMovies(): LiveData<List<Movie>> {
        return repository.getMovies()
    }
}