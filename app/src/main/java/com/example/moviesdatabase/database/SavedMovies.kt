package com.example.moviesdatabase.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesdatabase.data.Genre

@Entity
data class SavedMovies(
    @PrimaryKey var id: Int,
    var originalLanguage: String,
    var genres: String,//List<Genre>,
    var popularity: Double,
    var releaseDate: String,
    var overview: String,
    var title: String,
    var posterPath: String
)
