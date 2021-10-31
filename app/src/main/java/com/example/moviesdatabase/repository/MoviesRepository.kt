package com.example.moviesdatabase.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.moviesdatabase.api.TheMovieDBService
import com.example.moviesdatabase.data.Movie
import com.example.moviesdatabase.data.MovieDetails
import com.example.moviesdatabase.data.MoviesDao
import com.example.moviesdatabase.database.SavedMovies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

private const val API_KEY = "6a9a49ab06a8dbb88e7e296a11608515"
private const val IMAGE_URL_ROOT = "https://image.tmdb.org/t/p/w500/"

@Singleton
class MoviesRepository @Inject constructor(
    private val moviesService: TheMovieDBService,
    private val moviesDao: MoviesDao
) {

    private val NETWORK_PAGE_SIZE = 20

    fun getMoviesPlayingNow(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesDataSource(moviesService) }
        ).flow
    }


    suspend fun getMovieById(id: Int): SavedMovies? {

        return if (moviesDao.getMovieById(id) == null) {
            val response = moviesService.getMovieDetail(id, API_KEY)
            val responseToSavedMovies = fromMovieDetailsToSavedMovies(response)
            saveMovieToLocalDb(responseToSavedMovies)
            responseToSavedMovies
        } else moviesDao.getMovieById(id)
    }

    fun fromMovieDetailsToSavedMovies(details: MovieDetails): SavedMovies {

        var genresAsString = ""
        var genresList: List<String> = mutableListOf()
        details.genres.forEach() {
            genresList += it.name


            fun getGenresAsString(listGenres: List<String>): String {
                val stringBuilder = StringBuilder()
                for (genre in listGenres) {
                    stringBuilder.append(genre)
                    stringBuilder.append(", ")
                }
                return stringBuilder.toString().removeSuffix(", ")
            }
            genresAsString = getGenresAsString(genresList)
        }
        return SavedMovies(
            details.id, details.originalLanguage, genresAsString,
            details.popularity, details.releaseDate, details.overview.toString(),
            details.title, IMAGE_URL_ROOT + details.posterPath.toString()
        )
    }

    suspend fun saveMovieToLocalDb(movie: SavedMovies) {
        moviesDao.insertMovie(movie)
    }

    suspend fun getMovieByTitle(title: String): SavedMovies {
        return moviesDao.getMovieByTitle(title)
    }

}


//pendiente pasar a viewmodel?

/*
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

                    }
                    moviesLiveData.value = movies
                }
            }
        }

        override fun onFailure(call: Call<Genres>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
}*/
