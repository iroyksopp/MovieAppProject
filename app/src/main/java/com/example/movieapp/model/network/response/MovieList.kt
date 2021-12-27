package com.example.movieapp.model.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieList (
    @SerializedName("results")
    @Expose
    val results: List<Movie>? = null
)