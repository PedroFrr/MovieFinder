package com.raywenderlich.example.moviesapp.ui.pokemon_list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.ui.ItemTouchHelperCallback
import com.raywenderlich.example.moviesapp.ui.pokemons.Pokemon
import com.raywenderlich.example.moviesapp.ui.pokemons.PokemonAdapter
import com.raywenderlich.example.moviesapp.utils.prefs.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import kotlinx.coroutines.launch


class PokemonListFragment : Fragment() {

    private val repository by lazy { App.pokemonRepository }
    private val adapter by lazy { PokemonAdapter(::onPokemonSelected, ::onPokemonSwiped) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Check if user is already logged in. If he isn't redirect him to Login page, otherwise show list of movies
        if (!isUserLoggedIn()) {
            showLoginFragment()
        }else{
            initListeners()
            initUi()
            loadPokemonList()
            setupItemTouchHelper()
        }

    }

    private fun initUi(){
        movieRecyclerView.layoutManager = GridLayoutManager(context, 2)
        movieRecyclerView.adapter = adapter
    }

    private fun initListeners(){
        //TODO enable if we want to add new movies
//        fab.setOnClickListener {
//            showAddMovieFragment()
//        }
    }

    private fun loadPokemonList(){
//        val movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        val pokemonViewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
            activity?.let{
                lifecycleScope.launch {
                    pokemonViewModel.loadPokemons().observe(it, Observer<List<Pokemon>> { pokemons ->
                        adapter.setData(pokemons)
                    })
                }
        }
    }

    private fun isUserLoggedIn(): Boolean = SharedPrefManager().isUserLoggedIn()

    private fun onPokemonSelected(pokemon: Pokemon) {
        val action =
            PokemonListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(pokemon.id)
        view?.findNavController()?.navigate(action)
    }

    private fun setupItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(movieRecyclerView)
    }

    //Menu Setup
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Defines what to do on each menu option. For now only Login is defined
        when (item.itemId) {
            R.id.actionLogout -> logout()
        }
        return true
    }

    private fun logout() {
        val prefs = SharedPrefManager()
        prefs.setUserLoggedIn(false)
        showLoginFragment()
    }

    private fun showAddMovieFragment() = view?.let { Navigation.findNavController(it).navigate(R.id.mainToAddMovie) }

    private fun showLoginFragment() = view?.let { Navigation.findNavController(it).navigate(R.id.mainToLogin) }

    private fun onPokemonSwiped(pokemon: Pokemon) {
        lifecycleScope.launch {
            repository.deletePokemon(pokemon)
        }
    }

}
