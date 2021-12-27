package com.example.movieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.view.adapters.FavouriteMovieAdapter
import com.example.movieapp.viewmodel.MovieViewModel


class FavouriteMovieActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var rvFavouriteMovie: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_movie)

        rvFavouriteMovie = findViewById(R.id.rvFavouriteMovie)
        rvFavouriteMovie.layoutManager = GridLayoutManager(this, 2)
        val adapter = FavouriteMovieAdapter(this)
        rvFavouriteMovie.adapter = adapter

        adapter.onPosterClickListener = object : FavouriteMovieAdapter.OnPosterClickListener {
            override fun onPosterClick(position: Int) {
                val movie = adapter.favouriteMovieList[position]
                val intent = MovieDetailActivity.newIntent(this@FavouriteMovieActivity, movie.id)
                startActivity(intent)
            }
        }

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.favouriteMovieList.observe(this, Observer {
            adapter.favouriteMovieList = it
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemMain -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.itemFavourites -> {
                val intent = Intent(this, FavouriteMovieActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}