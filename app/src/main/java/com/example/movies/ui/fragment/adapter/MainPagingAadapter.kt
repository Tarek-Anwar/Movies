package com.example.movies.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movies.R
import com.example.movies.data.util.IMAGE_BASE_URL
import com.example.movies.databinding.MovieItemRvBinding
import com.example.movies.domain.entity.MovieModel

class MainPagingAadapter :
    PagingDataAdapter<MovieModel, MainPagingAadapter.MainViewHolder>(MovieDiffCallback()) {

    var onItemClick: ((MovieModel) -> Unit)? = null


    inner class MainViewHolder(private val itemBinding: MovieItemRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: MovieModel) {
            itemBinding.apply {
                itemBinding.imageMovieRv.load(IMAGE_BASE_URL + movie.posterPath) {
                    error(R.drawable.image_load_failed)
                    placeholder(R.drawable.icon_loading)
                    crossfade(800)
                }
                itemBinding.root.setOnClickListener {
                    onItemClick?.invoke(movie)
                }
            }
        }
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            MovieItemRvBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(
            oldItem: MovieModel, newItem: MovieModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieModel, newItem: MovieModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}