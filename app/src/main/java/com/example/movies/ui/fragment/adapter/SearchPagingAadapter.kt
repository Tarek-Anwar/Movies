package com.example.movies.ui.fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.MovieItemRvBinding
import com.example.movies.databinding.MovieItemSearchRvBinding
import com.example.movies.domain.entity.MovieModelRemote

class SearchPagingAadapter() :
    PagingDataAdapter<MovieModelRemote, SearchPagingAadapter.MainViewHolder>(MovieDiffCallback()) {
    private  val TAG = "SearchPagingAadapter"
        
    var onItemClick: ((MovieModelRemote) -> Unit)? = null

    inner class MainViewHolder(private val itemBinding: MovieItemSearchRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: MovieModelRemote) {
            Log.d(TAG, "bind: test "+ movie.overview)
            setUi(itemBinding, movie)
            itemBinding.root.setOnClickListener {
                onItemClick?.invoke(movie)
            }
        }

        private fun setUi(itemBinding: MovieItemSearchRvBinding, movie: MovieModelRemote) {
            Glide.with(itemBinding.root.context)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(itemBinding.posterMovieSearchImg)
            itemBinding.adultMovieSearchTv.text = movie.adult.toString()
            itemBinding.rateMovieSearchTv.text = movie.voteAverage.toString()
            itemBinding.titleMovieSearchTv.text = movie.title
            itemBinding.originalLanguageSearchMovie.text = movie.originalLanguage
            itemBinding.releaseDateSearchTv.text = movie.releaseDate
        }

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            MovieItemSearchRvBinding.inflate(
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