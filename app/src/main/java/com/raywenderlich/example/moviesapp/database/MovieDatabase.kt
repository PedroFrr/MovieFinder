package com.raywenderlich.example.moviesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raywenderlich.example.moviesapp.database.dao.MovieDao
import com.raywenderlich.example.moviesapp.ui.movies.Movie

const val DATABASE_VERSION = 1

@Database(
    version = DATABASE_VERSION,
    entities = [Movie::class]
)

abstract class MovieDatabase: RoomDatabase(){
    companion object {
        private const val DATABASE_NAME = "Cinema"
        fun buildDatabase(context: Context): MovieDatabase {
            return Room.databaseBuilder(
                context,
                MovieDatabase::class.java,
                DATABASE_NAME
            )
//                .allowMainThreadQueries() //TODO remove this for this assignment
                .build()
        }
    }

    abstract fun movieDao(): MovieDao
}