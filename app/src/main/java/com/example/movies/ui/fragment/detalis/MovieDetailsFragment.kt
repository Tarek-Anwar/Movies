package com.example.movies.ui.fragment.detalis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movies.databinding.FragmentMainDetailsBinding
import com.example.movies.domain.entity.MovieModelLocal
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment() {
    private var binding: FragmentMainDetailsBinding? = null
    private val viewModel: MovieDetailsViewModel by viewModels()
    val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainDetailsBinding.inflate(inflater, container, false)
        viewModel.getMovieDetail(args.id)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.movieDetailState.collect { movie ->
                movie?.let {
                    setData(it)
                }
            }
        }
        binding!!.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setData(movie: MovieModelLocal) {
        binding!!.overviewMovieTv.text = movie.overview
        binding!!.releaseDateTv.text = movie.releaseDate
        binding!!.titleMovieTv.text = movie.name
        binding!!.adultMovieTv.text = movie.adult.toString()
        binding!!.rateMovie.text = movie.voteAverage.toString()
        binding!!.originalLanguageMovie.text = movie.originalLanguage
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
            .into(binding!!.posterMovieImg)
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500" + movie.backdropPath)
            .into(binding!!.backdropMovieImg)
    }

    private val TAG = "MovieDetailsFragment"
}