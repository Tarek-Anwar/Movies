package com.example.movies.ui.fragment.detalis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movies.databinding.FragmentMainDetailsBinding
import com.example.movies.domain.entity.MovieModel
import com.example.movies.domain.entity.MovieModelX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
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


        movieDetailsObserver()
        handelBackPressed()
    }

    private fun handelBackPressed() {
        binding!!.backImage.setOnClickListener {
            findNavController().navigateUp()

        }
    }

    private fun setData(movie: MovieModelX) {
        binding!!.overviewMovieTv.text = movie.overview
        binding!!.releaseDateTv.text = movie.release_date
        binding!!.titleMovieTv.text = movie.original_title
        binding!!.adultMovieTv.text = movie.adult.toString()
        binding!!.rateMovie.text = movie.vote_average.toString()
        binding!!.originalLanguageMovie.text = movie.original_language
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .into(binding!!.posterMovieImg)
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500" + movie.backdrop_path)
            .into(binding!!.backdropMovieImg)
    }

    private fun movieDetailsObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieModelXStateFlow.flowWithLifecycle(lifecycle,Lifecycle.State.STARTED).collectLatest {
                setData(it)
            }
        }
    }

}