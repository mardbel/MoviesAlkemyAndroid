package com.example.moviesdatabase.data

import com.google.gson.annotations.SerializedName

data class MoviesPlayingNow(
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int
)