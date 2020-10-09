package com.raywenderlich.example.moviesapp.ui.pokemon_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.ui.pokemons.Pokemon

class PokemonViewModel(application: Application): AndroidViewModel(application){

    private val pokemonRepository by lazy { App.pokemonRepository}

    fun loadPokemons(): LiveData<List<Pokemon>> = pokemonRepository.getPokemons()

}