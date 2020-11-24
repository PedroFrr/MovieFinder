package com.raywenderlich.example.moviesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raywenderlich.example.moviesapp.database.dao.PokemonDao
import com.raywenderlich.example.moviesapp.model.Pokemon
import com.raywenderlich.example.moviesapp.utils.Converters
import com.raywenderlich.example.moviesapp.utils.DATABASE_NAME

const val DATABASE_VERSION = 1
@TypeConverters(Converters::class)

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
                    context.applicationContext, PokemonDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

