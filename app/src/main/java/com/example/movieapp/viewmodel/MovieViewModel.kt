package com.example.movieapp.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.movieapp.model.network.api.ApiFactory
import com.example.movieapp.model.database.AppDatabase
import com.example.movieapp.model.network.response.Movie
import com.example.movieapp.model.network.response.Trailer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val movieList = db.movieDao().getAllMovies()
    val favouriteMovieList = db.movieDao().getAllFavouriteMovies()
    val trailerList: MutableLiveData<List<Trailer>> = MutableLiveData()

    fun getMovieById(id: Int): LiveData<Movie> {
        return db.movieDao().getMovieById(id)
    }

    fun saveFavourite(id: Int, movie: Movie) = viewModelScope.launch {
        val task = db.movieDao().getFavouriteMovieById(id)
        if (task == null) {
            movie.let { db.movieDao().insertFavouriteMovie(it.fromMovieToFavourite()) }
            Toast.makeText(getApplication(), "Добавлено в избранные", Toast.LENGTH_LONG).show()
        } else  {
            db.movieDao().deleteFavouriteMovie(task)
            Toast.makeText(getApplication(), "Удалено из избранных", Toast.LENGTH_LONG).show()
        }
    }


  fun loadData(methodOfSort: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fetchedMovie = ApiFactory.api
                    .getMovies(methodOfSort = methodOfSort, page = page)
                withContext(Dispatchers.IO) {
                  db.movieDao().deleteAllMovies()
                    fetchedMovie.results?.let { db.movieDao().insertAll(it) }
                    Log.d("TESTING", "Success")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("TESTING", "Exc $e")
                }
            }
        }
    }

    fun loadTrailersData(id: Int) {
        viewModelScope.launch {
            val fetchedTrailers = ApiFactory.api.getTrailer(id)
            fetchedTrailers.results?.let { trailerList.value = it }
        }
    }
}