package com.example.movieapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.network.response.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val context: Context
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movieList: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onPosterClickListener: OnPosterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])

    }

    override fun getItemCount(): Int = movieList.size


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMovie = itemView.findViewById<ImageView>(R.id.ivSmallPoster)

        fun bind(movie: Movie) {
            Picasso.get().load(movie.getImagePosterURL()).into(ivMovie)
            itemView.setOnClickListener {
                onPosterClickListener?.onPosterClick(adapterPosition)
            }
        }
    }


    interface OnPosterClickListener {
        fun onPosterClick(position: Int)
    }
}
