package com.example.movies.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.MovieItemRvBinding
import com.example.movies.domain.entity.MovieModel

class MovieAdapter(private val movieItemListener: MovieItemListener) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var movieModelLocalList = listOf<MovieModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemBinding =
            MovieItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount() = movieModelLocalList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieModelLocalList.get(position))
        holder.itemView.setOnClickListener {
            movieModelLocalList.get(position).id?.let { it1 -> movieItemListener.onItemClick(it1) }
        }
    }

    class ViewHolder(private val itemBinding: MovieItemRvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: MovieModel) {
            Glide.with(itemBinding.root.context)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(itemBinding.imageMovieRv)
        }

    }

    interface MovieItemListener {
        fun onItemClick(id: Int)
    }
}