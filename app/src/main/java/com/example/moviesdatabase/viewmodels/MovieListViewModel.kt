package com.example.moviesdatabase.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdatabase.data.Movie
import com.example.moviesdatabase.repository.MoviesDataSource
import com.example.moviesdatabase.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieListViewModel : ViewModel() {

    val movies: Flow<PagingData<Movie>> = MoviesRepository
        .getMoviesPlayingNow()
        .cachedIn(viewModelScope)
}





