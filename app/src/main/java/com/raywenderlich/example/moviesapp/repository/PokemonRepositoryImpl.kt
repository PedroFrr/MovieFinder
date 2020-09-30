package com.raywenderlich.example.moviesapp.repository

import androidx.lifecycle.LiveData
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.database.dao.PokemonDao
import com.raywenderlich.example.moviesapp.model.Success
import com.raywenderlich.example.moviesapp.networking.BASE_URL
import com.raywenderlich.example.moviesapp.ui.Pokemon

const val IMAGE_BASE_URL = "https://pokeres.bastionbot.org/images/pokemon"
const val IMAGE_FORMAT = ".png"

class PokemonRepositoryImpl(private val pokemonDao: PokemonDao) : PokemonRepository {
    private val remoteApi by lazy { App.remoteApi }
    override suspend fun getPokemons(): LiveData<List<Pokemon>> {
        val result = remoteApi.loadPokemons()
        if (result is Success) {
            //If the DB is empty I'll first insert the API results into it
            if (pokemonDao.getAnyPokemon() == null ) {
                val regex = "(\\d+)(?!.*\\d)".toRegex()
                val pokemons = result.data.map { pokemon ->

                    val id = regex.find(pokemon.url)?.value
                    Pokemon(
                        id = id?.toInt() ?:  0 ,
                        name = pokemon.name,
                        imageUrl = "$IMAGE_BASE_URL/$id$IMAGE_FORMAT"
                    )
                }
                pokemonDao.addPokemons(pokemons)
            }

        }
        return pokemonDao.getPokemons()
    }
}