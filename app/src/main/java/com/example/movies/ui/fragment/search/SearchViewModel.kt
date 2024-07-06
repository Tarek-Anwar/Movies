package com.example.movies.ui.fragment.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.log
import com.example.movies.data.repo.MoviesRepositoryImpl
import com.example.movies.domain.entity.MovieModelLocal
import com.example.movies.domain.entity.MovieModelRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: MoviesRepositoryImpl
) : ViewModel() {

    private val _searchResults = MutableStateFlow<PagingData<MovieModelRemote>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<MovieModelRemote>> get() = _searchResults

    private fun getMoviesSearch(query: String) = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { repo.getSearchMovies(query) }
    ).flow.cachedIn(viewModelScope)

    fun searchMovies(query: String) {
        viewModelScope.launch {
            getMoviesSearch(query).collect{
                _searchResults.value = it
            }
        }
    }


}