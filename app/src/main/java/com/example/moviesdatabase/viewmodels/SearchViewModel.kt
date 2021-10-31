package com.example.moviesdatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdatabase.repository.MoviesRepository
import kotlinx.coroutines.launch


class SearchViewModel constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    fun getMovieByTitle(title: String){
        viewModelScope.launch {
            moviesRepository.getMovieByTitle(title)
        }
    }
}