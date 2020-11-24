package com.raywenderlich.example.moviesapp.ui.pokemon

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.model.Pokemon
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class PokemonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun showData(
        pokemon: Pokemon,
        onPokemonSelected : (Pokemon) -> Unit
    ) = with(itemView) {
        Glide.with(itemView)  //2
            .load(pokemon.imageUrl) //3
            .centerCrop() //4
            .placeholder(R.drawable.ic_image_place_holder) //5
            .error(R.drawable.ic_broken_image) //6
            .fallback(R.drawable.ic_no_image) //7
            .into(itemView.movieImage) //8
        pokemonName.text = pokemon.name

        setOnClickListener { onPokemonSelected(pokemon) }
    }

}