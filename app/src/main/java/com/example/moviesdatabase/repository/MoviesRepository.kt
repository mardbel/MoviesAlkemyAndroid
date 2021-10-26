package com.example.moviesdatabase.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesdatabase.api.RetrofitServiceBuilder
import com.example.moviesdatabase.api.TheMovieDBService
import com.example.moviesdatabase.data.Genres
import com.example.moviesdatabase.data.Movie
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "6a9a49ab06a8dbb88e7e296a11608515"
private const val IMAGE_URL_ROOT = "https://image.tmdb.org/t/p/w500/"


object MoviesRepository {
    private val moviesService: TheMovieDBService = RetrofitServiceBuilder(BASE_URL)
        .buildService(TheMovieDBService::class.java)

    private val moviesLiveData = MutableLiveData<List<Movie>>()
    const val NETWORK_PAGE_SIZE = 20

    fun getMoviesPlayingNow(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesDataSource() }

        ).flow



    }

    fun getMovieById(id: Int) : Movie {
        TODO()
    }

    //pendiente pasar a viewmodel?

    private fun setMovieData(movies: List<Movie>) {
        val call = moviesService.getGenreList(API_KEY, "en-US")
        call.enqueue(object : retrofit2.Callback<Genres> {
            override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                if (response.isSuccessful) {
                    val genres = response.body()?.genres
                    val genresMap = genres?.associateBy({ it.id }, { it.name })
                    genresMap?.let { map ->
                        movies.forEach { movie ->
                            movie.genresM = movie.genres.map { map[it] ?: "" }
                            movie.imageUrl = IMAGE_URL_ROOT + movie.posterPath
                        }
                        moviesLiveData.value = movies
                    }
                }
            }

            override fun onFailure(call: Call<Genres>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}