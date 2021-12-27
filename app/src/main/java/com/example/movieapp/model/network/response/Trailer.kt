package com.example.movieapp.model.network.response


import com.example.movieapp.model.network.api.Api.Companion.BASE_URL_YOUTUBE
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("key")
    @Expose
    val key: String? = null
) {
    fun toTrailerUrl(key: String): String {
        return BASE_URL_YOUTUBE + key
    }
}