package com.raywenderlich.example.moviesapp.di

import com.raywenderlich.example.moviesapp.database.PokemonDatabase
import com.raywenderlich.example.moviesapp.utils.prefs.SharedPrefManager
import org.koin.dsl.module

val dbModule = module {
    single {
        PokemonDatabase.buildDatabase(get())
    }

    single { get<PokemonDatabase>().pokemonDao() }

    single { SharedPrefManager() }

}
