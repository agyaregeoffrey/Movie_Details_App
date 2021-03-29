package com.dev.gka.abda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dev.gka.abda.model.Result


class MovieViewHolder private constructor (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val movieCover: ImageView = itemView.findViewById(R.id.movie_cover)
    private val title: TextView = itemView.findViewById(R.id.movie_title)
    private val releaseDate: TextView = itemView.findViewById(R.id.release_date)

    // http://image.tmdb.org/t/p/w500

    fun bind(movie: Result) {
        Glide.with(itemView)
            .load("http://image.tmdb.org/t/p/w500${movie.poster_path}")
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_loading_banner)
            )
            .centerCrop()
            .into(movieCover)
        title.text = movie.title
        releaseDate.text = movie.release_date
    }

    companion object {
        fun from(parent: ViewGroup): MovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(
                R.layout.item_movie_cover,
                parent, false
            )

            return MovieViewHolder(view)
        }
    }
}