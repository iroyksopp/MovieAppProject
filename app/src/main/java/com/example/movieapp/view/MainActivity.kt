package com.example.movieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.view.adapters.MovieAdapter
import com.example.movieapp.model.network.api.Api.Companion.SORT_BY_POPULARITY
import com.example.movieapp.model.network.api.Api.Companion.SORT_BY_TOP_RATED
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.viewmodel.MovieViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var methodOfSort: String
    private lateinit var viewModel: MovieViewModel
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieAdapter = MovieAdapter(this)
        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)
        binding.rvMovies.adapter = movieAdapter
        movieAdapter.onPosterClickListener = object : MovieAdapter.OnPosterClickListener {
            override fun onPosterClick(position: Int) {
                val movie = movieAdapter.movieList[position]
                val intent = MovieDetailActivity.newIntent(this@MainActivity, movie.id)
                startActivity(intent)
            }
        }

        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.abs_layout)
        }

        binding.actionBtn.setOnClickListener {
            viewModel.loadData(methodOfSort, page)
            page++
        }

        binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                with(binding) {
                    if (dy > 50 && !actionBtn.isShown) {
                        actionBtn.show()
                    }

                    if (dy < -10 && actionBtn.isShown) {
                        actionBtn.hide()
                    }
                }
            }
        })

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.movieList.observe(this, Observer {
            movieAdapter.movieList = it
        })

        binding.switchSort.isChecked = true
        binding.switchSort.setOnCheckedChangeListener { compoundButton, b ->
            page = 1
            methodOfSort(b)
        }
        binding.switchSort.isChecked = false
    }

    private fun methodOfSort(isTopRated: Boolean) {
        methodOfSort = if (isTopRated) {
            SORT_BY_TOP_RATED
        } else {
            SORT_BY_POPULARITY
        }
        viewModel.loadData(methodOfSort, page)
        page++
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


