package com.example.movies.ui.fragment.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.domain.entity.MovieModel
import com.example.movies.domain.usecase.MovieUseCase
import com.example.movies.ui.util.MoviesType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesUseCase: MovieUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _moviesFlow = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val moviesList: StateFlow<PagingData<MovieModel>> = _moviesFlow.asStateFlow()

    val moviesType: Channel<MoviesType> = Channel(capacity = Channel.UNLIMITED)


    private val _selectedTabPosition =
        MutableStateFlow(savedStateHandle.get("selected_tab_position") ?: 0)
    val selectedTabPosition: StateFlow<Int> get() = _selectedTabPosition

    fun saveSelectedTabPosition(position: Int) {
        viewModelScope.launch {
            _selectedTabPosition.value = position
            savedStateHandle.set("selected_tab_position", position)
        }
    }

    init {
        viewModelScope.launch {
            moviesType.consumeAsFlow().distinctUntilChanged()
                .collectLatest {
                    getMovies(it)
                }
        }

    }

    private suspend fun getMovies(movieType: MoviesType) {
        viewModelScope.launch(Dispatchers.IO) {
            val moviesResult = when (movieType) {
                MoviesType.UPCOMING -> moviesUseCase.getUpcomingMovies()
                MoviesType.TOP_RATED -> moviesUseCase.getTopRatedMovies()
                MoviesType.POPULAR -> moviesUseCase.getMostPopularMovies()
            }.cachedIn(viewModelScope)

            _moviesFlow.emit(moviesResult.first())
        }
    }

}
