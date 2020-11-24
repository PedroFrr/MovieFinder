package com.raywenderlich.example.moviesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.example.moviesapp.model.Pokemon
import com.raywenderlich.example.moviesapp.repository.PokemonRepository
import com.raywenderlich.example.moviesapp.utils.DEFAULT_IMAGE_URL

class AddPokemonViewModel(private val pokemonRepository: PokemonRepository): ViewModel() {
    var id = 0
    var name = ""
    var height = 0.0
    var weight = 0.0
    private var defaultImageUrl = DEFAULT_IMAGE_URL
    private val saveLiveData = MutableLiveData<Boolean>()

    fun getSaveLiveData(): LiveData<Boolean> = saveLiveData

    suspend fun savePokemon() {
        return if (canSavePokemon()) {
            val pokemonId = (0..99).random()
            val pokemon = Pokemon(id = pokemonId, name = name, height = height, weight = weight, imageUrl = defaultImageUrl)
            pokemonRepository.addPokemon(pokemon)
            saveLiveData.postValue(true)
        } else {
            saveLiveData.postValue(false)
        }
    }

    fun canSavePokemon(): Boolean {
        return name.isNotBlank() && height != 0.0 && weight != 0.0
    }


}