package com.raywenderlich.example.moviesapp.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.database.PokemonDatabase
import com.raywenderlich.example.moviesapp.model.Success
import com.raywenderlich.example.moviesapp.model.Pokemon
import com.raywenderlich.example.moviesapp.utils.IMAGE_BASE_URL
import com.raywenderlich.example.moviesapp.utils.IMAGE_FORMAT
import kotlinx.coroutines.coroutineScope

class PokemonDatabaseWorker(context: Context, workerParameters: WorkerParameters): CoroutineWorker(context, workerParameters) {
    private val remoteApi by lazy {App.remoteApi}

    override suspend fun doWork(): Result = coroutineScope {
        try {

            //TODO sync DB with remote API
            val result = remoteApi.loadPokemons()
            if (result is Success){
                //On the DB creation I'll prepopulate the DB with the result from the API
                val regex = "(\\d+)(?!.*\\d)".toRegex()
                val pokemons = result.data.map { pokemon ->
                    val id = regex.find(pokemon.url)?.value
                    Pokemon(
                        id = id?.toInt() ?: 0,
                        name = pokemon.name,
                        imageUrl = "$IMAGE_BASE_URL/$id$IMAGE_FORMAT"
                    )
                }
                val database = PokemonDatabase.getInstance(applicationContext)
                database.pokemonDao().addPokemons(pokemons)
                Log.d(TAG, "Sync successful")
                Result.success()
            }else{
                Result.failure()
            }


        }catch (ex: Exception) {
            Log.e(TAG, "Error pokemon database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "PokemonDatabaseWorker"
    }

}