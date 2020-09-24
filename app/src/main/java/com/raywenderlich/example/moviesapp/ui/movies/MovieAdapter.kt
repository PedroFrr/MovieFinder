package com.raywenderlich.example.moviesapp.ui.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.ui.ItemTouchHelperListener
import java.util.*

class MovieAdapter(private var movies: MutableList<Movie>, val clickListener: MovieClickListener) :
    RecyclerView.Adapter<MovieViewHolder>(), ItemTouchHelperListener {

    private val repository by lazy {
        App.repository
    }


    interface MovieClickListener {
        fun listItemClicked(movieId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movie, parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieImageView?.setImageResource(movies[position].poster)
        holder.movieTitleTextVIew?.text = movies[position].title
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(movies[position].id)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onItemMove(
        recyclerView: RecyclerView,
        fromPosition: Int,
        toPosition: Int
    ): Boolean {
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

        //TODO add this with a ViewModel and observe data in order to improve
//        repository.deleteMovie(movie)
        notifyItemRemoved(position)

    }
}