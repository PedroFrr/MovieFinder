package com.raywenderlich.example.moviesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.example.moviesapp.repository.PokemonRepository
import com.raywenderlich.example.moviesapp.model.Pokemon

class PokemonListViewModel(private val pokemonRepository: PokemonRepository): ViewModel(){

    fun loadPokemons(): LiveData<List<Pokemon>> = pokemonRepository.getPokemons()

    suspend fun deletePokemon(pokemon: Pokemon) = pokemonRepository.deletePokemon(pokemon)

}