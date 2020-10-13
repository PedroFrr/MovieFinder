package com.raywenderlich.example.moviesapp.viewmodels

import androidx.lifecycle.ViewModel
import com.raywenderlich.example.moviesapp.repository.PokemonRepository
import com.raywenderlich.example.moviesapp.model.Pokemon

class PokemonDetailViewModel(private val pokemonRepository : PokemonRepository) : ViewModel(){

    suspend fun getPokemonById(pokemonId: Int): Pokemon = pokemonRepository.getPokemonById(pokemonId)

}