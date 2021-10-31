package com.example.moviesdatabase.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.moviesdatabase.R
import com.example.moviesdatabase.database.SavedMovies
import com.example.moviesdatabase.databinding.ActivityMovieDetailBinding
import com.example.moviesdatabase.repository.MoviesRepository
import com.example.moviesdatabase.viewmodels.MovieDetailViewModel
import com.example.moviesdatabase.viewmodels.MovieListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var intent = intent
        val movieId: Int = intent.getIntExtra("selected movie", 1)
        viewModel.getMovieByIdFromRepo(movieId)

        viewModel.state.observe(this, Observer {
            when (it) {
                is MovieDetailViewModel.State.Failure -> displayError(it.cause)
                is MovieDetailViewModel.State.Loading -> showProgressBar()
                is MovieDetailViewModel.State.Success -> showMovie(it.movie)
            }
        })
    }
        fun showMovie(savedMovies: SavedMovies) {
            binding.progressBar.isVisible = false
            var descriptionTV = binding.descriptionTextView
            var tittleTV = binding.tittleTextView
            var posterIV = binding.posterImageView
            var ratingTV = binding.ratingTextView
            var releaseDateTV = binding.releaseDateTv
            var originalLanguageTV = binding.originalLanguageTv
            var genreTV = binding.genreTv
            descriptionTV.text = savedMovies.overview
            tittleTV.text = savedMovies.title
            ratingTV.text = savedMovies.popularity.toString()
            originalLanguageTV.text = savedMovies.originalLanguage.uppercase()
            releaseDateTV.text = savedMovies.releaseDate.substring(0,4)
            genreTV.text = savedMovies.genres
            Glide.with(this)
                .load(savedMovies.posterPath)
                .into(posterIV)
        }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
    }

    private fun displayError(cause: Throwable) {
        binding.progressBar.isVisible = false
        var layout = binding.rootLayout
        val snackbar = Snackbar.make(
            layout, "cause",
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }
}
