package com.raywenderlich.example.moviesapp

import android.app.Application
import com.raywenderlich.example.moviesapp.database.PokemonDatabase
import com.raywenderlich.example.moviesapp.networking.RemoteApi
import com.raywenderlich.example.moviesapp.networking.buildApiService
import com.raywenderlich.example.moviesapp.repository.PokemonRepository
import com.raywenderlich.example.moviesapp.repository.PokemonRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class App() : Application() {

    companion object {
        private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        private lateinit var instance: App
        private val DATABASE: PokemonDatabase by lazy { PokemonDatabase.buildDatabase(instance, applicationScope) }
        val pokemonRepository: PokemonRepository by lazy {PokemonRepositoryImpl(DATABASE.pokemonDao())}
        private val service by lazy { buildApiService()}
        val remoteApi by lazy {RemoteApi(service)}

        fun getAppContext() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}