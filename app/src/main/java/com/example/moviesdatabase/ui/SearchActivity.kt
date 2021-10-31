package com.example.moviesdatabase.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.example.moviesdatabase.database.SavedMovies
import com.example.moviesdatabase.databinding.ActivitySearchBinding
import com.example.moviesdatabase.viewmodels.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchET.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                val searchedMovie: String = binding.searchET.text.toString()

                //hides keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(binding.searchET.windowToken, 0)
                if (searchedMovie.isNullOrBlank()) {
                    Toast.makeText(this, "enter a valid movie title", Toast.LENGTH_LONG)
                        .show()

                } else {

                }
            }
            false
        }

    }
}