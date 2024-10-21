package com.example.assignment3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.R
import com.example.assignment3.data.Entity.MovieShow
import com.example.assignment3.databinding.ItemMovieShowBinding


class MovieShowAdapter : ListAdapter<MovieShow, MovieShowAdapter.MovieShowViewHolder>(MovieShowDiffCallback()) {

    private var onItemClickListener: ((MovieShow) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieShowViewHolder {
        val binding = ItemMovieShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieShowViewHolder, position: Int) {
        val movieShow = getItem(position)
        holder.bind(movieShow)
    }

    inner class MovieShowViewHolder(private val binding: ItemMovieShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieShow: MovieShow) {
            binding.title.text = movieShow.title
            binding.type.text = movieShow.type
            binding.status.text = movieShow.status

            // Set background color of status based on its value
            val statusColor = when (movieShow.status) {
                "To Watch" -> R.color.status_to_watch
                "Watching" -> R.color.status_watching
                "Completed" -> R.color.status_completed
                else -> R.color.status_default
            }

            binding.status.setBackgroundResource(statusColor)

            // Set click listener for the item
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(movieShow)
            }
        }
    }

    fun setOnItemClickListener(listener: (MovieShow) -> Unit) {
        onItemClickListener = listener
    }

    class MovieShowDiffCallback : DiffUtil.ItemCallback<MovieShow>() {
        override fun areItemsTheSame(oldItem: MovieShow, newItem: MovieShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieShow, newItem: MovieShow): Boolean {
            return oldItem == newItem
        }
    }
}