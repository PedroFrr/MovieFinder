package com.raywenderlich.example.moviesapp.networking

import com.raywenderlich.example.moviesapp.model.Failure
import com.raywenderlich.example.moviesapp.model.Result
import com.raywenderlich.example.moviesapp.model.Success
import com.raywenderlich.example.moviesapp.model.response.PokemonResponse

const val BASE_URL = "https://pokeapi.co/api/v2/"

class RemoteApi(private val apiService: RemoteApiService) {

    suspend fun loadPokemons(): Result<List<PokemonResponse>> =
        try {
            val response = apiService.getAllPokemons()
            val results = response.results
            Success(results)

        } catch (error: Throwable) {
            Failure(error)
        }

}