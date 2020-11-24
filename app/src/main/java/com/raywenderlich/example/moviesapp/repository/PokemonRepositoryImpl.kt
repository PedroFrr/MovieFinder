package com.raywenderlich.example.moviesapp.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.example.moviesapp.database.dao.PokemonDao
import com.raywenderlich.example.moviesapp.model.Pokemon
import com.raywenderlich.example.moviesapp.model.Success
import com.raywenderlich.example.moviesapp.model.response.PokemonResponse
import com.raywenderlich.example.moviesapp.networking.PokemonApi
import com.raywenderlich.example.moviesapp.utils.IMAGE_BASE_URL
import com.raywenderlich.example.moviesapp.utils.IMAGE_FORMAT

class PokemonRepositoryImpl(private val pokemonDao: PokemonDao, private val pokemonApi: PokemonApi) : PokemonRepository {

    override fun getPokemons(): LiveData<List<Pokemon>> = pokemonDao.getPokemons()

    override suspend fun getPokemonById(pokemonId: Int): Pokemon = pokemonDao.getPokemonById(pokemonId)

    override suspend fun deletePokemon(pokemon: Pokemon) = pokemonDao.deletePokemon(pokemon)

    override suspend fun addPokemons(pokemons: List<Pokemon>) = pokemonDao.addPokemons(pokemons)

    override suspend fun addPokemon(pokemon: Pokemon) = pokemonDao.addPokemon(pokemon)

    override suspend fun loadPokemons() {
        val results = pokemonApi.loadPokemons()

        if (results is Success) {
            val pokemons = results.data
            savePokemonsList(pokemons)
        }
    }

    private suspend fun savePokemonsList (result: List<PokemonResponse>){

        val regex = "(\\d+)(?!.*\\d)".toRegex()
        val pokemons = result.map { pokemon ->
            val id = regex.find(pokemon.url)?.value
            Pokemon(
                id = id?.toInt() ?: 0,
                name = pokemon.name,
                imageUrl = "$IMAGE_BASE_URL/$id$IMAGE_FORMAT"
            )
        }

        pokemonDao.addPokemons(pokemons)

    }


}