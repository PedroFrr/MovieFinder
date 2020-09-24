package com.raywenderlich.example.moviesapp.ui.movies

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Movie(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var releaseDate: String,
    var title: String,
    var summary: String,
    var poster: Int
)
