package com.raywenderlich.example.moviesapp

import android.app.Application
import com.raywenderlich.example.moviesapp.database.MovieDatabase
import com.raywenderlich.example.moviesapp.networking.RemoteApi
import com.raywenderlich.example.moviesapp.networking.buildApiService
import com.raywenderlich.example.moviesapp.repository.MovieRepository
import com.raywenderlich.example.moviesapp.repository.MovieRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class App() : Application() {

    companion object {
        private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        private lateinit var instance: App
        private val database: MovieDatabase by lazy { MovieDatabase.buildDatabase(instance, applicationScope) }
        val repository: MovieRepository by lazy { MovieRepositoryImpl(database.movieDao()) }
        private val service by lazy { buildApiService()}
        val remoteApi by lazy {RemoteApi(service)}

        fun getAppContext() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}