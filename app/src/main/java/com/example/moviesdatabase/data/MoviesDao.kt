package com.example.moviesdatabase.data

import androidx.room.*
import com.example.moviesdatabase.database.SavedMovies

@Dao
interface MoviesDao {

    @Query("SELECT * FROM SavedMovies WHERE id = :id")
    suspend fun getMovieById(id: Int): SavedMovies

    @Query("SELECT * FROM SavedMovies WHERE title = :title")
    suspend fun getMovieByTitle(title: String): SavedMovies

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(city: SavedMovies)

    @Delete
    suspend fun deleteMovie(city: SavedMovies)

}