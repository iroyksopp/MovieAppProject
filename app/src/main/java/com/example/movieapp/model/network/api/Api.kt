package com.example.movieapp.model.network.api

import com.example.movieapp.model.network.response.MovieList
import com.example.movieapp.model.network.response.TrailerList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query(QUERY_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_LANGUAGE) language: String = LANGUAGE,
        @Query(QUERY_SORT_BY) methodOfSort: String,
        @Query(QUERY_PAGE) page: Int = 1,
        @Query(QUERY_MIN_VOTE_COUNT) minVoteCount: Int = 1000
    ): MovieList

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailer(
        @Path("movie_id") id: Int,
        @Query(QUERY_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_LANGUAGE) language: String = LANGUAGE

    ): TrailerList


    companion object {
        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_LANGUAGE = "language"
        private const val QUERY_SORT_BY = "sort_by"
        private const val QUERY_PAGE = "page"
        private const val API_KEY = "344b48111e9efb80904590da580f3cd4"
        private const val QUERY_MIN_VOTE_COUNT = "vote_count.gte"
        private const val LANGUAGE = "ru-RU"


        const val SORT_BY_TOP_RATED = "vote_average.desc"
        const val SORT_BY_POPULARITY = "popularity.desc"
        const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/"
        const val SMALL_POSTER_SIZE = "w185"
        const val BIG_POSTER_SIZE = "w780"
        const val BASE_URL_YOUTUBE = "https://www.youtube.com/watch?v="

    }
}