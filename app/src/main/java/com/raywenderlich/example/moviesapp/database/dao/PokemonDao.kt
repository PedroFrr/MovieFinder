package com.raywenderlich.example.moviesapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raywenderlich.example.moviesapp.ui.pokemons.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM Pokemon")
    fun getPokemons(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM Pokemon WHERE id = :pokemonId")
    suspend fun getPokemonById(pokemonId: Int): Pokemon

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemons(pokemons: List<Pokemon>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPokemon(pokemon: Pokemon)

    @Query("SELECT * FROM Pokemon LIMIT 1")
    fun getAnyPokemon(): Pokemon?

    @Delete
    suspend fun deletePokemon(pokemon: Pokemon)
}