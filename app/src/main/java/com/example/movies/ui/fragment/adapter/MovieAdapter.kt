package com.example.movies.ui.fragment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.MovieItemRvBinding
import com.example.movies.domain.entity.MovieModel

class MovieAdapter(private val movieItemListener: MovieItemListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var arrayList = listOf<MovieModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemBinding =
            MovieItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.bind(arrayList.get(position))
        holder.bind(arrayList.get(position))
        holder.itemView.setOnClickListener {
            movieItemListener.onItemClick(arrayList.get(position).id)
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