package com.example.movies.ui.fragment.detalis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repo.MoviesRepositoryImpl
import com.example.movies.domain.entity.MovieDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MoviesRepositoryImpl
) : ViewModel() {

    private var _movieModelStateFlow = MutableStateFlow(MovieDetailModel())
    val movieModelXStateFlow get() = _movieModelStateFlow.asStateFlow()

    fun getMovieDetail(id: Int) =
        viewModelScope.launch() {
            _movieModelStateFlow.emit(repository.getMovieDetails(id))
        }

}