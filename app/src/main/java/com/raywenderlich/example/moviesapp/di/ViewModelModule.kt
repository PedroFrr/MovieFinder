package com.raywenderlich.example.moviesapp.di

import com.raywenderlich.example.moviesapp.viewmodels.AddPokemonViewModel
import com.raywenderlich.example.moviesapp.viewmodels.PokemonDetailViewModel
import com.raywenderlich.example.moviesapp.viewmodels.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AddPokemonViewModel(get()) }

    viewModel { PokemonDetailViewModel(get()) }

    viewModel { PokemonListViewModel(get()) }
}


