package com.example.moviesdatabase.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdatabase.databinding.ActivityMovieListBinding
import com.example.moviesdatabase.viewmodels.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private val adapter = MovieListAdapter(this) {
        val idItemClicked = it
        val intent = Intent(this@MovieListActivity, MovieDetailActivity::class.java)
            .putExtra("selected movie", idItemClicked)
        startActivity(intent)
    }
    private val viewModel by viewModels<MovieListViewModel>()
    private var searchJob: Job? = null

    fun searchMovies() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchMovies().collect {
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
        GridLayoutManager(this, 2, RecyclerView.VERTICAL, false).apply {
            recycler.layoutManager = this
        }
        searchMovies()

        binding.searchIcon.setOnClickListener {
            val intent = Intent(this@MovieListActivity, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}

/*val searchET = binding.searchET.text.toString()
fun showToastMenuXml(item: MenuItem){
    val toast = Toast.makeText(this, "$searchET", Toast.LENGTH_LONG);
    toast.show()
}

val searchIcon = binding
val filtredMovie = binding.myEditText.text.toString()
val intent = Intent(this@MovieListActivity, MovieDetailActivity::class.java)
    .putExtra("selected movie", idItemClicked)
startActivity(intent)
*/




