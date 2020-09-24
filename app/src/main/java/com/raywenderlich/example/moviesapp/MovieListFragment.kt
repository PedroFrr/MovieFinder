package com.raywenderlich.example.moviesapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.example.moviesapp.database.common.utils.prefs.SharedPrefManager
import com.raywenderlich.example.moviesapp.ui.ItemTouchHelperCallback
import com.raywenderlich.example.moviesapp.ui.movies.Movie
import com.raywenderlich.example.moviesapp.ui.movies.MovieAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.launch


class MovieListFragment : Fragment(), MovieAdapter.MovieClickListener {

    private val repository by lazy {
        App.repository
    }

    companion object {
        fun newInstance(): MovieListFragment {
            return MovieListFragment()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentClickListener = this

        activity?.let {
            if (!isUserLoggedIn()) {
                showLoginFragment()
            }
            lifecycleScope.launch {
                val movies = repository.getMovies() as MutableList<Movie>
                val adapter = MovieAdapter(movies, fragmentClickListener)
                movieRecyclerView.layoutManager =
                    LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
                movieRecyclerView.adapter = adapter
            }

        }


        fab.setOnClickListener {
            showAddMovieFragment()
        }
        setupItemTouchHelper()
    }

    private fun isUserLoggedIn(): Boolean = SharedPrefManager().isUserLoggedIn()

    override fun listItemClicked(movieId: String) {
        val action =
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieId)
        view?.findNavController()?.navigate(action)
    }

    private fun setupItemTouchHelper() {
        val fragmentClickListener = this
        lifecycleScope.launch {
            val movies = repository.getMovies() as MutableList<Movie>
            val adapter = MovieAdapter(movies, fragmentClickListener)
            val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
            itemTouchHelper.attachToRecyclerView(movieRecyclerView)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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

    private fun showAddMovieFragment() {
        view?.let { Navigation.findNavController(it).navigate(R.id.mainToAddMovie) }

    }

    private fun showLoginFragment() {
        view?.let { Navigation.findNavController(it).navigate(R.id.mainToLogin) }
    }


}
