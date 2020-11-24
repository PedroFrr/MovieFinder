package com.raywenderlich.example.moviesapp.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.example.moviesapp.model.Pokemon

interface PokemonRepository {

    fun getPokemons(): LiveData<List<Pokemon>>
    suspend fun getPokemonById(pokemonId: Int): Pokemon
    suspend fun deletePokemon(pokemon: Pokemon)
    suspend fun addPokemons(pokemons: List<Pokemon>)
    suspend fun addPokemon(pokemon: Pokemon)

    suspend fun loadPokemons()

}