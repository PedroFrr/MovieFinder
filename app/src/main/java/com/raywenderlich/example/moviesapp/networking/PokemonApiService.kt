package com.raywenderlich.example.moviesapp.networking

import com.raywenderlich.example.moviesapp.model.response.GetListPokemons
import retrofit2.http.GET

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getAllPokemons(): GetListPokemons


}