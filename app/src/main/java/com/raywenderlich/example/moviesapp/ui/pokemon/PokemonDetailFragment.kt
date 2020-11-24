package com.raywenderlich.example.moviesapp.ui.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.viewmodels.PokemonDetailViewModel
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailFragment() : Fragment() {

    private val viewModel : PokemonDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = PokemonDetailFragmentArgs.fromBundle(it)
            val pokemonId = args.movieId

            lifecycleScope.launch {
                val pokemon = viewModel.getPokemonById(pokemonId)

                launch(context = Dispatchers.Main) {
                    activity?.let {activity ->
                        Glide.with(activity)  //2
                            .load(pokemon.imageUrl) //3
                            .centerCrop() //4
                            .placeholder(R.drawable.ic_image_place_holder) //5
                            .error(R.drawable.ic_broken_image) //6
                            .fallback(R.drawable.ic_no_image) //7
                            .into(activity.movieDetailImageView) //8
                        pokemonNameDetail.text = pokemon.name
                        pokemonWeight.text = pokemon.weight.toString()
                        pokemonHeight.text = pokemon.height.toString()
                    }
                }
            }
        }
    }

}