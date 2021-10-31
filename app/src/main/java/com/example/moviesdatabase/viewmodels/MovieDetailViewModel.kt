package com.example.moviesdatabase.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdatabase.database.SavedMovies
import com.example.moviesdatabase.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    fun getMovieByIdFromRepo(id: Int) {
        _state.value = State.Loading()
        viewModelScope.launch {
            val movieDetails = moviesRepository.getMovieById(id)
            if (
                movieDetails == null) {
                throw MovieNotFoundedException()
            } else _state.postValue(State.Success(movieDetails))
        }
    }

    sealed class State {
        class Success(val movie: SavedMovies) : State()
        class Failure(val cause: Throwable) : State()
        class Loading : State()
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    class MovieNotFoundedException : Exception("Please enter a valid movie title")
}