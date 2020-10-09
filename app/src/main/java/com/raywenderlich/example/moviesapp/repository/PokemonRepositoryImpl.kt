package com.raywenderlich.example.moviesapp.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.example.moviesapp.database.dao.PokemonDao
import com.raywenderlich.example.moviesapp.ui.pokemons.Pokemon

class PokemonRepositoryImpl(private val pokemonDao: PokemonDao) : PokemonRepository {

    override fun getPokemons(): LiveData<List<Pokemon>> = pokemonDao.getPokemons()

    override suspend fun getPokemonById(pokemonId: Int): Pokemon = pokemonDao.getPokemonById(pokemonId)

    override suspend fun deletePokemon(pokemon: Pokemon) = pokemonDao.deletePokemon(pokemon)

    override suspend fun addPokemons(pokemons: List<Pokemon>) = pokemonDao.addPokemons(pokemons)
}