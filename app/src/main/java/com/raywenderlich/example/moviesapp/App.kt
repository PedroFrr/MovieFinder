package com.raywenderlich.example.moviesapp

import android.app.Application
import com.raywenderlich.example.moviesapp.database.PokemonDatabase
import com.raywenderlich.example.moviesapp.networking.RemoteApi
import com.raywenderlich.example.moviesapp.networking.buildApiService
import com.raywenderlich.example.moviesapp.repository.PokemonRepository
import com.raywenderlich.example.moviesapp.repository.PokemonRepositoryImpl
import com.raywenderlich.example.moviesapp.viewmodels.AddPokemonViewModelFactory
import com.raywenderlich.example.moviesapp.viewmodels.PokemonDetailViewModelFactory
import com.raywenderlich.example.moviesapp.viewmodels.PokemonListViewModelFactory

class App() : Application() {

    companion object {
        private lateinit var instance: App
        private val DATABASE: PokemonDatabase by lazy { PokemonDatabase.buildDatabase(instance) }
        private val service by lazy { buildApiService()}
        private val pokemonRepository: PokemonRepository by lazy {PokemonRepositoryImpl(DATABASE.pokemonDao())}
        val remoteApi by lazy {RemoteApi(service)}
        val pokemonListViewModelFactory by lazy { PokemonListViewModelFactory(pokemonRepository)}
        val pokemonDetailViewModelFactory by lazy { PokemonDetailViewModelFactory(pokemonRepository)}
        val addPokemonViewModelFactory by lazy { AddPokemonViewModelFactory(pokemonRepository)}

        fun getAppContext() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}