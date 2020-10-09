package com.raywenderlich.example.moviesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.database.dao.PokemonDao
import com.raywenderlich.example.moviesapp.ui.pokemons.Pokemon
import com.raywenderlich.example.moviesapp.utils.DATABASE_NAME
import com.raywenderlich.example.moviesapp.workers.PokemonDatabaseWorker
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.TimeUnit

const val DATABASE_VERSION = 1

@Database(version = DATABASE_VERSION, entities = [Pokemon::class])

abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {

        private var INSTANCE: PokemonDatabase? = null

        fun buildDatabase(context: Context, coroutineScope: CoroutineScope): PokemonDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(PlayerDatabaseCallback(coroutineScope, context))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class PlayerDatabaseCallback(private val scope: CoroutineScope, private val context: Context) : RoomDatabase.Callback() {
        private val remoteApi by lazy { App.remoteApi }
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val workManager = WorkManager.getInstance(context)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_ROAMING)
                .build()
            val request = PeriodicWorkRequestBuilder<PokemonDatabaseWorker>(15, TimeUnit.MINUTES)//repeats each 15 minutes
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork("PokemonDatabaseWork", ExistingPeriodicWorkPolicy.KEEP, request)

        }

    }


}