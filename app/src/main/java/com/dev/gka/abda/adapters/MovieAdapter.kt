package com.dev.gka.abda.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.gka.abda.model.MovieResult

class MovieAdapter(
    private val onClickListener: OnClickListener,
    private val movieResults: List<MovieResult>
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val result = movieResults[position]
        holder.bind(result)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(result)
        }
    }


    override fun getItemCount() = movieResults.size

    class OnClickListener(val clickListener: (movieResultProperty: MovieResult) -> Unit) {
        fun onClick(movieResultProperty: MovieResult) = clickListener(movieResultProperty)
    }
}