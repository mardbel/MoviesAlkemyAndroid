package com.example.moviesdatabase.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("genre_ids") var genres: List<Int>,
    @SerializedName("overview") val description: String
)

    : Parcelable {

    var imageUrl: String = ""
    var genresM: List<String> = mutableListOf()

    fun getGenresAsString(): String {
        val stringBuilder = StringBuilder()
        for (genre in genresM) {
            stringBuilder.append(genre)
            stringBuilder.append(", ")
        }
        return stringBuilder.toString().removeSuffix(", ")
    }
}