package com.raywenderlich.example.moviesapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raywenderlich.example.moviesapp.ui.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM Pokemon")
    fun getPokemons(): LiveData<List<Pokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemons(pokemons: List<Pokemon>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPokemon(pokemon: Pokemon)

    @Query("SELECT * FROM Pokemon LIMIT 1")
    suspend fun getAnyPokemon(): Pokemon?
}