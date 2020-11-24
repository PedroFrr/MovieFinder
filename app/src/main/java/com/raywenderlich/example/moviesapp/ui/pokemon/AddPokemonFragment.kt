package com.raywenderlich.example.moviesapp.ui.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.utils.hideKeyboard
import com.raywenderlich.example.moviesapp.viewmodels.AddPokemonViewModel
import kotlinx.android.synthetic.main.fragment_add_pokemon.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class   AddPokemonFragment : Fragment() {

    private val viewModel: AddPokemonViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservables()
    }

    private fun initListeners() {
        addPokemon.setOnClickListener {
            createPokemon()
        }
        //TODO redo to select avatar from list of monsters like Creaturemon
//        moviePosterPlaceholder.setOnClickListener {
//            takePictureWithCamera()
//        }
    }

    private fun createPokemon() {
        viewModel.name = pokemonName.text.toString()
        viewModel.height = pokemonHeight.text.toString().toDouble()
        viewModel.weight = pokemonWeight.text.toString().toDouble()

        lifecycleScope.launch {
            viewModel.savePokemon()
        }

    }

    private fun initObservables() {
        activity?.let {
            viewModel.getSaveLiveData().observe(viewLifecycleOwner, Observer { saved ->
                if (saved) {
                    Toast.makeText(activity, getString(R.string.pokemon_added), Toast.LENGTH_SHORT)
                        .show()
                    hideKeyboard()
                    view?.let { view ->
                        Navigation.findNavController(view).navigate(R.id.addMovieToMain)
                    }
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.mandatoryAddMovieMessage),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }

    }
}