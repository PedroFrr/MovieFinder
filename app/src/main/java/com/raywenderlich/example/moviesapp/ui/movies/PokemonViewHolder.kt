package com.raywenderlich.example.moviesapp.ui.movies

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.ui.Pokemon
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun showData(
        pokemon: Pokemon,
        onMovieSelected : (Movie) -> Unit
    ) = with(itemView) {
        //TODO Load image from URL using Picasso or Glide
        Glide.with(itemView)  //2
            .load(pokemon.imageUrl) //3
            .centerCrop() //4
            .placeholder(R.drawable.ic_image_place_holder) //5
            .error(R.drawable.ic_broken_image) //6
            .fallback(R.drawable.ic_no_image) //7
            .into(itemView.movieImage) //8
//        movieImage.setImageResource(R.drawable.creature_cow_01)
        movieTitle.text = pokemon.name

        //TODO refactor click listener to show Pokemon detail
//        setOnClickListener { onMovieSelected(movie) }
    }

}