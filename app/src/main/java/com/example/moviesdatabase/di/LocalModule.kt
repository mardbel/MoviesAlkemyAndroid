package com.example.moviesdatabase.di

import android.content.Context
import androidx.room.Room
import com.example.moviesdatabase.data.MoviesDB
import com.example.moviesdatabase.data.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    fun providesMoviesDb(@ApplicationContext context: Context): MoviesDB {
        return Room.databaseBuilder(context, MoviesDB::class.java, "savedMovies").build()
    }

    @Provides
    fun providesSavedMoviesDao(moviesdb: MoviesDB): MoviesDao {
        return moviesdb.savedMoviesDao()
    }
}