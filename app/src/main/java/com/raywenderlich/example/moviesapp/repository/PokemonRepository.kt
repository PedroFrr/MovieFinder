package com.raywenderlich.example.moviesapp.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.example.moviesapp.model.response.PokemonResponse
import com.raywenderlich.example.moviesapp.ui.Pokemon

interface PokemonRepository {

    suspend fun getPokemons(): LiveData<List<Pokemon>>
    suspend fun getPokemonById(pokemonId: Int): Pokemon
    suspend fun deletePokemon(pokemon: Pokemon)

}