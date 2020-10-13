package com.raywenderlich.example.moviesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import com.raywenderlich.example.moviesapp.database.dao.PokemonDao
import com.raywenderlich.example.moviesapp.model.Pokemon
import com.raywenderlich.example.moviesapp.utils.DATABASE_NAME
import com.raywenderlich.example.moviesapp.workers.PokemonDatabaseWorker
import java.util.concurrent.TimeUnit

const val DATABASE_VERSION = 1

@Database(version = DATABASE_VERSION, entities = [Pokemon::class])

abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {

        private var INSTANCE: PokemonDatabase? = null

        fun getInstance(context: Context): PokemonDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        fun buildDatabase(context: Context): PokemonDatabase {
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
                    .addCallback(PlayerDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class PlayerDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
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