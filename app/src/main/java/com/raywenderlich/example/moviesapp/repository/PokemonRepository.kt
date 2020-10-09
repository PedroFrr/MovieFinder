package com.raywenderlich.example.moviesapp.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.example.moviesapp.ui.pokemons.Pokemon

interface PokemonRepository {

    fun getPokemons(): LiveData<List<Pokemon>>
    suspend fun getPokemonById(pokemonId: Int): Pokemon
    suspend fun deletePokemon(pokemon: Pokemon)
    suspend fun addPokemons(pokemons: List<Pokemon>)

}