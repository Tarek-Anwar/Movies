package com.example.movies.ui.fragment.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.data.repo.MoviesRepositoryImpl
import com.example.movies.domain.entity.MovieModelRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: MoviesRepositoryImpl, private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /*   val pagingDataFlow = MutableStateFlow<PagingData<MovieModelRemote>?>(null)

      var recyclerViewState: Parcelable? = null
  */

    private val _moviesFlow = MutableStateFlow<PagingData<MovieModelRemote>>(PagingData.empty())
    val moviesList: StateFlow<PagingData<MovieModelRemote>> = _moviesFlow

    val moviesType: Channel<String> = Channel(capacity = Channel.UNLIMITED)


    private val _selectedTabPosition =
        MutableStateFlow(savedStateHandle.get("selected_tab_position") ?: -1)
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
                _moviesFlow.emit(getCustomMovies(it).first())
            }
        }

    }

    private fun getCustomMovies(query: String) : Flow<PagingData<MovieModelRemote>> =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { repo.getCustomMovies(query) }).flow.cachedIn(viewModelScope)



}
