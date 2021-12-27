package com.example.movieapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.network.response.FavouriteMovie
import com.squareup.picasso.Picasso

class FavouriteMovieAdapter(
    private val context: Context
) : RecyclerView.Adapter<FavouriteMovieAdapter.FavouriteMovieViewHolder>() {

    var favouriteMovieList: List<FavouriteMovie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onPosterClickListener: OnPosterClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return FavouriteMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteMovieViewHolder, position: Int) {
        holder.bind(favouriteMovieList[position])
    }

    override fun getItemCount(): Int = favouriteMovieList.size


    inner class FavouriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMovie = itemView.findViewById<ImageView>(R.id.ivSmallPoster)

        fun bind(movie: FavouriteMovie) {
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
