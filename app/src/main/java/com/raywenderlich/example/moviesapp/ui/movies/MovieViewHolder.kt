package com.raywenderlich.example.moviesapp.ui.movies

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var movieImageView: ImageView? = itemView.movieImage
    var movieTitleTextVIew: TextView? = itemView.movieTitle

}