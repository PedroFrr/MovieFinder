package com.raywenderlich.example.moviesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.raywenderlich.example.moviesapp.ui.movies.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailFragment() : Fragment() {

    private lateinit var movie: Movie

    private val repository by lazy {
        App.movieRepository
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = MovieDetailFragmentArgs.fromBundle(it)
            val movieId = args.movieId

            lifecycleScope.launch {
                movie = repository.getMovieById(movieId)

                launch(context = Dispatchers.Main) {

                    movieDetailImageView.setImageResource(movie.poster)
                    movieTitleTextView.text = movie.title
                    releaseDateTextView.text = movie.releaseDate
                }

            }


        }

    }

    companion object {

        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newInstance(movieId: Int): MovieDetailFragment {
            val bundle = Bundle()
            bundle.putInt(EXTRA_MOVIE_ID, movieId)
            val fragment = MovieDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}