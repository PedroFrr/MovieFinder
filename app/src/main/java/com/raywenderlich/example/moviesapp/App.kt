package com.raywenderlich.example.moviesapp

import android.app.Application
import com.raywenderlich.example.moviesapp.database.MovieDatabase
import com.raywenderlich.example.moviesapp.repository.MovieRepository
import com.raywenderlich.example.moviesapp.repository.MovieRepositoryImpl

class App : Application() {

    companion object {
        private lateinit var instance: App
        private val database: MovieDatabase by lazy {
            MovieDatabase.buildDatabase(instance)
        }

        val repository: MovieRepository by lazy {
            MovieRepositoryImpl(database.movieDao())
        }

        fun getAppContext() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


//            if (repository.getMovies().isEmpty()){
//
//                val movies = mutableListOf<Movie>(
//                    Movie(releaseDate = "2020-02-10", title = "Return", summary = "Summary", poster = R.drawable.creature_app_whistle_1),
//                    Movie( releaseDate = "2020-02-10", title =  "Tenet", summary = "Summary", poster = R.drawable.creature_cow_01),
//                    Movie( releaseDate = "2020-02-10",title =  "After we Collided", summary = "Summary", poster = R.drawable.creature_bear_sleepy),
//                    Movie( releaseDate = "2020-02-13", title = "Unhinged", summary = "Summary",poster =  R.drawable.creature_bird_blue_fly_1),
//                    Movie( releaseDate = "2020-02-14", title = "My Hero Academia",summary =  "Summary",poster =  R.drawable.creature_bug_insect_happy),
//                    Movie( releaseDate = "2020-02-15", title = "Ordem Moral", summary = "Summary",poster =  R.drawable.creature_bug_spider),
//                    Movie( releaseDate = "2020-02-11", title = "One Piece", summary = "Summary", poster = R.drawable.creature_cat_derp),
//                    Movie( releaseDate = "2020-02-11", title = "Steins Gate", summary = "Summary", poster = R.drawable.creature_dog_bone),
//                    Movie( releaseDate = "2020-02-11", title = "Hygurashi", summary = "Summary",poster =  R.drawable.creature_duck_yellow_1),
//                    Movie( releaseDate = "2020-02-11", title = "Haykyu", summary = "Summary", poster = R.drawable.creature_frog_hungry),
//                    Movie( releaseDate = "2020-02-11", title = "Ping pong animation", summary = "Summary", poster = R.drawable.creature_head_fox),
//                )
//
//                repository.addMovies(movies)
//            }
    }


}