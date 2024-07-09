package com.example.movies.ui.fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movies.R
import com.example.movies.data.util.IMAGE_BASE_URL
import com.example.movies.databinding.MovieItemSearchRvBinding
import com.example.movies.domain.entity.MovieModel

class SearchPagingAadapter() :
    PagingDataAdapter<MovieModel, SearchPagingAadapter.MainViewHolder>(MovieDiffCallback()) {

    var onItemClick: ((MovieModel) -> Unit)? = null

    inner class MainViewHolder(private val itemBinding: MovieItemSearchRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: MovieModel) {
            setUi(itemBinding, movie)
            itemBinding.root.setOnClickListener {
                onItemClick?.invoke(movie)
            }
        }

        private fun setUi(itemBinding: MovieItemSearchRvBinding, movie: MovieModel) {
            itemBinding.posterMovieSearchImg.load(IMAGE_BASE_URL +movie.posterPath){
                error(R.drawable.image_load_failed)
                placeholder(R.drawable.icon_loading)
                crossfade(1000)
            }
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

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}