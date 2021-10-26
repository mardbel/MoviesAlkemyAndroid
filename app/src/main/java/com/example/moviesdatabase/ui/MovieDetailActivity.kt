package com.example.moviesdatabase.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.moviesdatabase.data.Movie
import com.example.moviesdatabase.databinding.ActivityMovieDetailBinding
import com.example.moviesdatabase.viewmodels.MovieListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel by viewModels<MovieListViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = intent
        val position: Int = intent.getIntExtra("selected movie", 0)



      /*  lifecycleScope.launchWhenResumed {
            viewModel.movies.collect {


                var descriptionTV = binding.descriptionTextView
                var tittleTV = binding.tittleTextView
                var posterIV = binding.posterImageView
                var ratingTV = binding.ratingTextView
                descriptionTV.text = it[position].description
                tittleTV.text = it[position].title
                ratingTV.text = it[position].rating.toString()
                Glide.with(this)
                    .load(it[position].imageUrl)
                    .into(posterIV)
            }
        }*/
    }
}