package com.raywenderlich.example.moviesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.database.dao.MovieDao
import com.raywenderlich.example.moviesapp.database.dao.PokemonDao
import com.raywenderlich.example.moviesapp.ui.Pokemon
import com.raywenderlich.example.moviesapp.ui.movies.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val DATABASE_VERSION = 1

@Database(
    version = DATABASE_VERSION,
    entities = [Movie::class, Pokemon::class]
)

abstract class MovieDatabase: RoomDatabase(){
    companion object {
        private const val DATABASE_NAME = "Cinema"
        private var INSTANCE: MovieDatabase? = null

        fun buildDatabase(context: Context, coroutineScope: CoroutineScope): MovieDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DATABASE_NAME)
                    .addCallback(PlayerDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class PlayerDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val playerDao = database.movieDao()
                    prePopulateDatabase(playerDao)
                }
            }
        }

        private suspend fun prePopulateDatabase(movieDao: MovieDao) {

            val movies = mutableListOf<Movie>(
                Movie(releaseDate = "2020-02-10", title = "Return", summary = "Summary", poster = R.drawable.creature_app_whistle_1),
                Movie( releaseDate = "2020-02-10", title = "Tenet", summary = "Summary", poster = R.drawable.creature_cow_01),
                Movie( releaseDate = "2020-02-10",title =  "After we Collided", summary = "Summary", poster = R.drawable.creature_bear_sleepy),
                Movie( releaseDate = "2020-02-13", title = "Unhinged", summary = "Summary",poster =  R.drawable.creature_bird_blue_fly_1),
                Movie( releaseDate = "2020-02-14", title = "My Hero Academia",summary =  "Summary",poster =  R.drawable.creature_bug_insect_happy),
                Movie( releaseDate = "2020-02-15", title = "Ordem Moral", summary = "Summary",poster =  R.drawable.creature_bug_spider),
                Movie( releaseDate = "2020-02-11", title = "One Piece", summary = "Summary", poster = R.drawable.creature_cat_derp),
                Movie( releaseDate = "2020-02-11", title = "Steins Gate", summary = "Summary", poster = R.drawable.creature_dog_bone),
                Movie( releaseDate = "2020-02-11", title = "Hygurashi", summary = "Summary",poster =  R.drawable.creature_duck_yellow_1),
                Movie( releaseDate = "2020-02-11", title = "Haykyu", summary = "Summary", poster = R.drawable.creature_frog_hungry),
                Movie( releaseDate = "2020-02-11", title = "Ping pong animation", summary = "Summary", poster = R.drawable.creature_head_fox)
            )

            movieDao.addMovies(movies)
        }
    }

    abstract fun movieDao(): MovieDao

    abstract fun pokemonDao(): PokemonDao
}