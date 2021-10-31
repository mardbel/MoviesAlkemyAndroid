package com.example.moviesdatabase.api

import com.example.moviesdatabase.data.Genres
import com.example.moviesdatabase.data.MovieDetails
import com.example.moviesdatabase.data.PopularMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBService {

    @GET("movie/popular")
    suspend fun getMoviesPlayingNow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): PopularMovies

    @GET("genre/movie/list")
    fun getGenreList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<Genres>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): MovieDetails
}