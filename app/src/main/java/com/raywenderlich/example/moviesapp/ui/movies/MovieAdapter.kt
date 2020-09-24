package com.raywenderlich.example.moviesapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.ui.ItemTouchHelperListener
import java.util.*

class MovieAdapter(
    private val onMovieSelected: (Movie) -> Unit,
    private val onMovieSwiped: (Movie) -> Unit
) :
    RecyclerView.Adapter<MovieViewHolder>(), ItemTouchHelperListener {

    private val movies = mutableListOf<Movie>()
    private val repository by lazy {App.repository}

    interface OnItemSwipeListener {
        fun onItemSwipe(movie: Movie)
    }

    fun setData(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.showData(movies[position], onMovieSelected)

    override fun getItemCount(): Int = movies.size

    override fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(movies, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition) {
                Collections.swap(movies, i, i)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val movie = movies[position]
        movies.removeAt(position)
        onMovieSwiped(movie)
        notifyItemRemoved(position)

    }
}