package com.example.moviesdatabase.api

import com.example.moviesdatabase.data.Genres
import com.example.moviesdatabase.data.MoviesPlayingNow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBService {
    @GET("movie/now_playing")

    suspend fun getMoviesPlayingNow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): MoviesPlayingNow

    @GET("genre/movie/list")
    fun getGenreList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
        ): Call<Genres>
}