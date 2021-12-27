package com.example.movieapp.model.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.model.network.api.Api
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourite_movies")
data class FavouriteMovie(

    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,

    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null,

    @SerializedName("overview")
    @Expose
    val overview: String? = null,

    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null
) {
    fun getImagePosterURL(): String {
        return Api.BASE_POSTER_URL + Api.SMALL_POSTER_SIZE + posterPath
    }

}

