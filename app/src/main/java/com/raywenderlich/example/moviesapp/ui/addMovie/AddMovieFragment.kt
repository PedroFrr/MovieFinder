package com.raywenderlich.example.moviesapp.ui.addMovie

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.R
import com.raywenderlich.example.moviesapp.ui.movies.Movie
import kotlinx.android.synthetic.main.fragment_add_movie.*
import kotlinx.coroutines.launch

class AddMovieFragment : Fragment() {

    private val repository by lazy {
        App.repository
    }

    private var pictureTaken: Boolean = false
    private var imageUri: Uri? = null

    companion object {
        private const val TAKE_PHOTO_REQUEST_CODE = 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

    }

    private fun initListeners(){
        addMovie.setOnClickListener {
             createMovie(it)
        }
        moviePosterPlaceholder.setOnClickListener {
            takePictureWithCamera()
        }
    }

    private fun takePictureWithCamera() {

        val captureIntent = Intent(Intent.ACTION_GET_CONTENT)
        captureIntent.type = "image/*"
        startActivityForResult(captureIntent, TAKE_PHOTO_REQUEST_CODE)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            moviePosterPlaceholder.setImageURI(imageUri)
        }
    }

    private fun createMovie(view: View) {
        val title = movieTitle.text.toString()
        val summary = movieSummary.text.toString()
        val releaseDate = movieReleaseDate.text.toString()

        if (title.isNotBlank() && summary.isNotBlank()) {
            val movie = Movie(
                title = title,
                summary = summary,
                releaseDate = releaseDate,
                poster = R.drawable.creature_cow_01
            )

            lifecycleScope.launch {
                repository.addMovie(movie)
                Toast.makeText(activity, getString(R.string.movie_added), Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.addMovieToMain)
            }


        } else {
            activity?.let {
                Toast.makeText(
                    activity,
                    getString(R.string.mandatoryAddMovieMessage),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


    }
}