package com.example.moviesdatabase.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesdatabase.database.SavedMovies

@Database(
    entities = [SavedMovies::class],
    version = 1
)
abstract class MoviesDB : RoomDatabase() {

    abstract fun savedMoviesDao(): MoviesDao
}