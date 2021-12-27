package com.example.movieapp.model.network.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class TrailerList(

    @SerializedName("results")
    @Expose
    val results: List<Trailer>? = null

)
