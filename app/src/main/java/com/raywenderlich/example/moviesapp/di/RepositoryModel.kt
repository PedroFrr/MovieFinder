package com.raywenderlich.example.moviesapp.di

import com.raywenderlich.example.moviesapp.repository.PokemonRepository
import com.raywenderlich.example.moviesapp.repository.PokemonRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
//    factory<PokemonRepository> { PokemonRepositoryImpl(get()) }
    single { PokemonRepositoryImpl(get(), get()) as PokemonRepository}
}