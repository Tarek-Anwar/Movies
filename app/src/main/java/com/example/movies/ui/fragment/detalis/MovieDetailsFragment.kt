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
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.util.IMAGE_BASE_URL
import com.example.movies.databinding.FragmentMovieDetailsBinding
import com.example.movies.domain.entity.MovieDetailModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var binding: FragmentMovieDetailsBinding? = null
    private val viewModel: MovieDetailsViewModel by viewModels()
    val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
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

    private fun setData(movie: MovieDetailModel) {
        binding!!.overviewMovieTv.text = movie.overview
        binding!!.releaseDateTv.text = movie.releaseDate
        binding!!.titleMovieTv.text = movie.originalTitle
        binding!!.adultMovieTv.text = movie.isAdult.toString()
        binding!!.rateMovie.text = movie.voteAverage.toString()
        binding!!.originalLanguageMovie.text = movie.originalLanguage
        Glide.with(requireContext())
            .load(IMAGE_BASE_URL+movie.posterPath)
            .into(binding!!.posterMovieImg)
        Glide.with(requireContext())
            .load(IMAGE_BASE_URL+movie.backdropPath)
            .into(binding!!.backdropMovieImg)
        //binding!!.backdropMovieImg.load(IMAGE_BASE_URL+movie.backdropPath)
        //binding!!.posterMovieImg.load(IMAGE_BASE_URL+movie.posterPath)


    }

    private fun movieDetailsObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieModelXStateFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    if(it!=null){
                        setData(it)
                    }
                }
        }
    }

}