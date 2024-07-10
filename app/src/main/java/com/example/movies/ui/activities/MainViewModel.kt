package com.example.movies.ui.activities

import androidx.lifecycle.ViewModel
import com.example.movies.domain.entity.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var _movieModelStateFlow = MutableStateFlow<MovieModel?>(null)
    val movieModelStateFlow get() = _movieModelStateFlow.asStateFlow()

    fun setMovieDetail(movie: MovieModel) {
        _movieModelStateFlow.value = movie
    }


}