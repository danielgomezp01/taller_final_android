package com.example.moviesapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "poster_path") val posterPath: String?
)
