package com.example.movies.ui.fragment.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.usecase.GetMoviesPopularUserCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val useCase = GetMoviesPopularUserCase()

    private val _moviesState: MutableStateFlow<MovieScreenState?> = MutableStateFlow(
        MovieScreenState(
            emptyList(),
            true,
            null
        )
    )
    val moviesState: StateFlow<MovieScreenState?> = _moviesState

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _moviesState.value = _moviesState.value?.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch(errorHandler)
        {
            val movies = useCase()
            _moviesState.value = _moviesState.value?.copy(movies, false)
        }
    }

}