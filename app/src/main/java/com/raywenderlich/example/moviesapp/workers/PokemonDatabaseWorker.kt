package com.raywenderlich.example.moviesapp.workers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class PokemonDatabaseWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters), KoinComponent{

    private val pokemonRepository by inject<PokemonRepository>()

    override suspend fun doWork(): Result {
        withContext(Dispatchers.Main) {
            Toast.makeText(App.getAppContext(), "Running Background Sync", Toast.LENGTH_LONG).show()
        }

        pokemonRepository.loadPokemons()
        Log.d(TAG, "Sync successful")
        return Result.success()
    }

    companion object {
        private const val TAG = "PokemonDatabaseWorker"
    }

}