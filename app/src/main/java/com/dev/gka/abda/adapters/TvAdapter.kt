package com.dev.gka.abda.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.gka.abda.model.TvResult

class TvAdapter(
    private val onClickListener: OnClickListener,
    private val results: List<TvResult>
) : RecyclerView.Adapter<TvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        return TvViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val result = results[position]
        holder.bind(result)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(result)
        }
    }


    override fun getItemCount() = results.size

    class OnClickListener(val clickListener: (resultProperty: TvResult) -> Unit) {
        fun onClick(resultProperty: TvResult) = clickListener(resultProperty)
    }
}