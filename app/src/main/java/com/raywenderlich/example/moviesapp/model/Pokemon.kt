package com.raywenderlich.example.moviesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Pokemon(
    @PrimaryKey
    val id: Int ,
    val name: String,
    //TODO remove default values when I get the weight and height from the API
    val weight: Double = 90.5,
    val height: Double = 1.7,
    val imageUrl: String
)