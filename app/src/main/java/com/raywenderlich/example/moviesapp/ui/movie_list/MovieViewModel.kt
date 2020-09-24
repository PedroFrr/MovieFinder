package com.raywenderlich.example.moviesapp.ui.movie_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.database.MovieDatabase
import com.raywenderlich.example.moviesapp.repository.MovieRepository
import com.raywenderlich.example.moviesapp.ui.movies.Movie

//TODO implement ViewModel - see the benefits, study this topic
class MovieListViewModel(application: Application): AndroidViewModel(application) {

    private val repository by lazy { App.repository}

//    init {
//        val playerDao = MovieDatabase
//            .buildDatabase(application, viewModelScope)
//            .movieDao()
//    }

    fun getMovies(): LiveData<List<Movie>> {
        return repository.getMovies()
    }
}