package com.raywenderlich.example.moviesapp.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String
)