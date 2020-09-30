package com.raywenderlich.example.moviesapp.ui.movie_list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.utils.prefs.SharedPrefManager
import com.raywenderlich.example.moviesapp.ui.ItemTouchHelperCallback
import com.raywenderlich.example.moviesapp.ui.movies.Movie
import com.raywenderlich.example.moviesapp.ui.movies.MovieAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.launch


class MovieListFragment : Fragment() {

    private val repository by lazy { App.repository }
    private val adapter by lazy { MovieAdapter(::onMovieSelected, ::onMovieSwipe) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Check if user is already logged in. If he isn't redirect him to Login page, otherwise show list of movies
        if (!isUserLoggedIn()) {
            showLoginFragment()
        }else{
            initListeners()
            initUi()
            loadMovieList()
            setupItemTouchHelper()
        }

    }

    private fun initUi(){
        movieRecyclerView.layoutManager = LinearLayoutManager(context)
        movieRecyclerView.adapter = adapter
    }

    private fun initListeners(){
        fab.setOnClickListener {
            showAddMovieFragment()
        }
    }

    private fun loadMovieList(){
        val movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        activity?.let{
            movieViewModel.getMovies().observe(it, Observer<List<Movie>> { movies ->
                adapter.setData(movies)
            })
        }

    }

    private fun isUserLoggedIn(): Boolean = SharedPrefManager().isUserLoggedIn()

    private fun onMovieSelected(movie: Movie) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie.id )
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

    private fun onMovieSwipe(movie: Movie) {
        lifecycleScope.launch {
            repository.deleteMovie(movie)
        }
    }

}
