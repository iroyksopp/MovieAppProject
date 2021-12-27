package com.example.movieapp.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.view.adapters.TrailerAdapter
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.model.network.response.Movie
import com.example.movieapp.viewmodel.MovieViewModel
import com.squareup.picasso.Picasso


class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var rvTrailers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvTrailers = findViewById(R.id.rvTrailers)
        rvTrailers.layoutManager = LinearLayoutManager(this)
        val trailersAdapter = TrailerAdapter(this)
        rvTrailers.adapter = trailersAdapter

        if (!intent.hasExtra(MOVIE_ID)) {
            finish()
            return
        }

        val id = intent.getIntExtra(MOVIE_ID, -1)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getMovieById(id).observe(this, Observer {
            with(binding) {
                Picasso.get().load(it.getImageBigPosterURL()).into(ivDetailPoster)
                tvTitle.text = it.title
                tvOriginalTitle.text = it.originalTitle
                tvRating.text = it.voteAverage.toString()
                tvReleaseDate.text = it.releaseDate
                tvOverview.text = it.overview
            }
        })

        viewModel.loadTrailersData(id)
        viewModel.trailerList.observe(this, Observer {
            trailersAdapter.trailerList = it
        })

        trailersAdapter.onTrailerClickListener = object : TrailerAdapter.OnTrailerClickListener {
            override fun onTrailerClick(url: String) {
                val intentTrailer = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intentTrailer)
            }
        }

        fun observeMovieDetail(id: Int, action: (Movie) -> Unit) {
            viewModel.getMovieById(id).observe(this, {
                action.invoke(it)
            })
        }

        binding.ivAddFavourite.setOnClickListener {
            observeMovieDetail(id) {
                viewModel.saveFavourite(id, it)
            }
        }
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


    companion object {
        private const val MOVIE_ID = "id"

        fun newIntent(context: Context, id: Int?): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_ID, id)
            return intent
        }
    }
}