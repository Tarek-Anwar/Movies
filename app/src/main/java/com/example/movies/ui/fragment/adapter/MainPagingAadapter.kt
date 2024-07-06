package com.example.movies.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.util.IMAGE_BASE_URL
import com.example.movies.databinding.MovieItemRvBinding
import com.example.movies.domain.entity.MovieModelRemote

class MainPagingAadapter() :
    PagingDataAdapter<MovieModelRemote, MainPagingAadapter.MainViewHolder>(MovieDiffCallback()) {

    var onItemClick: ((MovieModelRemote) -> Unit)? = null

    inner class MainViewHolder(private val itemBinding: MovieItemRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: MovieModelRemote) {
            Glide.with(itemBinding.root.context)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .error(R.drawable.image_load_failed)
                .into(itemBinding.imageMovieRv)
           // itemBinding.imageMovieRv.load(IMAGE_BASE_URL+movie.posterPath)
            itemBinding.root.setOnClickListener {
                onItemClick?.invoke(movie)
            }
        }

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            MovieItemRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieModelRemote>() {
        override fun areItemsTheSame(
            oldItem: MovieModelRemote,
            newItem: MovieModelRemote
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieModelRemote,
            newItem: MovieModelRemote
        ): Boolean {
            return oldItem == newItem
        }
    }
}