package com.raywenderlich.example.moviesapp.ui.movies

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun showData(
        movie: Movie,
        onMovieSelected : (Movie) -> Unit
    ) = with(itemView) {
        movieImage.setImageResource(movie.poster)
        movieTitle.text = movie.title

        setOnClickListener { onMovieSelected(movie) }
    }

}