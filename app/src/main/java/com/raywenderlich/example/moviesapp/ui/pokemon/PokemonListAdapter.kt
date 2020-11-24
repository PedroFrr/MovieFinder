package com.raywenderlich.example.moviesapp.ui.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.model.Pokemon
import com.raywenderlich.example.moviesapp.ui.ItemTouchHelperListener
import java.util.*

class PokemonListAdapter(
    private val onPokemonSelected: (Pokemon) -> Unit,
    private val onPokemonSwiped: (Pokemon) -> Unit
) :
    RecyclerView.Adapter<PokemonListViewHolder>(), ItemTouchHelperListener {
    private val pokemons = mutableListOf<Pokemon>()

    fun setData(newPokemons: List<Pokemon>) {
        pokemons.clear()
        pokemons.addAll(newPokemons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pokemon, parent, false)

        return PokemonListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) = holder.showData(pokemons[position], onPokemonSelected)

    override fun getItemCount(): Int = pokemons.size

    override fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(pokemons, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition) {
                Collections.swap(pokemons, i, i)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = pokemons[position]
        pokemons.removeAt(position)
        onPokemonSwiped(pokemon)
        notifyItemRemoved(position)

    }
}