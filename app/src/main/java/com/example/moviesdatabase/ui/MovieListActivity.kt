package com.example.moviesdatabase.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesdatabase.data.Movie
import com.example.moviesdatabase.databinding.ActivityMovieListBinding
import com.example.moviesdatabase.viewmodels.MovieListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private val adapter = MovieListAdapter()
    private val viewModel by viewModels<MovieListViewModel>()
    private var searchJob: Job? = null

    private fun search() {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.movies.collect(){
                adapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.recyclerMovieList
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
        search()




        adapter.setOnClickListener(object : MovieListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {


                val intent = Intent(this@MovieListActivity, MovieDetailActivity::class.java)
                    .putExtra("selected movie", position)
                startActivity(intent)
            }
        })
    }

}



