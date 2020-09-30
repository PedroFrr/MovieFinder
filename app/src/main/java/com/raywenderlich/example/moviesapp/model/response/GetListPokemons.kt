package com.raywenderlich.example.moviesapp.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetListPokemons(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResponse>
)