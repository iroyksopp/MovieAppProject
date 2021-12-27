package com.example.movieapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.network.response.Trailer

class TrailerAdapter(
    val context: Context
) : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    var trailerList: List<Trailer> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onTrailerClickListener: OnTrailerClickListener? = null


    interface OnTrailerClickListener {
        fun onTrailerClick(url: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_trailer, parent, false)
        return TrailerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bind(trailerList[position])
    }

    override fun getItemCount(): Int = trailerList.size

    inner class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tvTrailerName)
        fun bind(trailer: Trailer) {
            tvName.text = trailer.name
            itemView.setOnClickListener {
                trailerList[adapterPosition].key?.let { it ->
                    trailer.toTrailerUrl(
                        it
                    )
                }?.let { it1 -> onTrailerClickListener?.onTrailerClick(it1) }
            }
        }
    }
}