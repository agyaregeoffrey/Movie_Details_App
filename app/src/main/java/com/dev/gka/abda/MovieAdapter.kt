package com.dev.gka.abda

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.gka.abda.model.Result

class MovieAdapter(
    private val onClickListener: OnClickListener,
    private val results: List<Result>
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val result = results[position]
        holder.bind(result)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(result)
        }
    }


    override fun getItemCount() = results.size

    class OnClickListener(val clickListener: (resultProperty: Result) -> Unit) {
        fun onClick(resultProperty: Result) = clickListener(resultProperty)
    }
}