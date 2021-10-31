package com.example.moviesdatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdatabase.data.Movie
import com.example.moviesdatabase.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    var currentSearchResult: Flow<PagingData<Movie>>? = null

    fun searchMovies(): Flow<PagingData<Movie>> {
        val newResult: Flow<PagingData<Movie>> = moviesRepository.getMoviesPlayingNow()
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}



