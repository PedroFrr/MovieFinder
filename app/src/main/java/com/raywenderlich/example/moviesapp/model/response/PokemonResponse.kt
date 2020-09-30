package com.raywenderlich.example.moviesapp.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val name: String,
    val url: String
)