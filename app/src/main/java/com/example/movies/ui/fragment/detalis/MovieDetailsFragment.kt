package com.example.movies.ui.fragment.detalis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.movies.R
import com.example.movies.data.util.IMAGE_BASE_URL
import com.example.movies.databinding.FragmentMovieDetailsBinding
import com.example.movies.domain.entity.MovieModel
import com.example.movies.ui.activities.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var binding: FragmentMovieDetailsBinding? = null
    private val mainViewModel : MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
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

    private fun setData(movie: MovieModel) {
        binding!!.overviewMovieTv.text = movie.overview
        binding!!.releaseDateTv.text = movie.releaseDate
        binding!!.titleMovieTv.text = movie.title
        binding!!.adultMovieTv.text = movie.adult.toString()
        binding!!.rateMovie.text = "%.1f".format(movie.voteAverage)
        binding!!.originalLanguageMovie.text = movie.originalLanguage
        binding!!.backdropMovieImg.load(IMAGE_BASE_URL + movie.backdropPath) {
            error(R.drawable.image_load_failed)
            placeholder(R.drawable.icon_loading)
            crossfade(800)
        }
        binding!!.posterMovieImg.load(IMAGE_BASE_URL + movie.posterPath) {
            error(R.drawable.image_load_failed)
            placeholder(R.drawable.icon_loading)
            crossfade(800)
        }


    }

    private fun movieDetailsObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.movieModelStateFlow.collectLatest {
                setData(it!!)
            }
        }
    }

}