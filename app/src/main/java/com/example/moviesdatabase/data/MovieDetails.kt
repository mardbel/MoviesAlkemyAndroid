package com.example.moviesdatabase.data

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("overview") val overview: String?,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String?
)