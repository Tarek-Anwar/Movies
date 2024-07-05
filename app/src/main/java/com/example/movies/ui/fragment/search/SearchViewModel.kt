package com.example.movies.ui.fragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movies.data.repo.MoviesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: MoviesRepositoryImpl
) : ViewModel() {

    fun getMoviesSearch(query: String) = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { repo.getSearchMovies(query) }
    ).flow.cachedIn(viewModelScope)
}