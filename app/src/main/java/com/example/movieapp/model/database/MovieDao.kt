package com.example.movieapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.model.network.response.FavouriteMovie
import com.example.movieapp.model.network.response.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_entity")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavouriteMovies(): LiveData<List<FavouriteMovie>>

    @Query("SELECT * FROM movie_entity WHERE id == :id limit 1")
    fun getMovieById(id : Int): LiveData<Movie>

    @Query("SELECT * FROM favourite_movies WHERE id == :id limit 1")
    suspend fun getFavouriteMovieById(id : Int): FavouriteMovie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieList: List<Movie>)

    @Query("DELETE FROM movie_entity")
    suspend fun deleteAllMovies()

    @Insert
    suspend fun insertFavouriteMovie(movie: FavouriteMovie)

    @Delete
    suspend fun deleteFavouriteMovie(movie: FavouriteMovie)
}