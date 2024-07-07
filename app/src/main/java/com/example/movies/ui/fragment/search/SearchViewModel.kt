package com.example.movies.ui.fragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.data.paging.MoviesType
import com.example.movies.data.repo.MoviesRepositoryImpl
import com.example.movies.domain.entity.MovieModel
import com.example.movies.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesUseCase: MovieUseCase,
) : ViewModel() {

    private val _searchResults = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<MovieModel>> get() = _searchResults

    val keyWordMovies: Channel<String> = Channel(capacity = Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            keyWordMovies.consumeAsFlow().distinctUntilChanged()
                .collectLatest { getMovies(it) }
        }
    }

    private suspend fun getMovies(quary: String) {
        viewModelScope.launch {
            val moviesResult = moviesUseCase.getSearchMovies(quary).cachedIn(viewModelScope)
            _searchResults.emit(moviesResult.first())
        }
    }


}