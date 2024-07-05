package com.example.movies.ui.fragment.main

import android.os.Parcelable
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MoviesRepositoryImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  /*   val pagingDataFlow = MutableStateFlow<PagingData<MovieModelRemote>?>(null)

    var recyclerViewState: Parcelable? = null
*/

    private val _selectedTabPosition =
        MutableStateFlow(savedStateHandle.get("selected_tab_position") ?: 0)
    val selectedTabPosition: StateFlow<Int> get() = _selectedTabPosition

    fun saveSelectedTabPosition(position: Int) {
        viewModelScope.launch {
            _selectedTabPosition.value = position
            savedStateHandle.set("selected_tab_position", position)
        }
    }

     fun getCustomMovies(query: String) = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { repo.getCustomMovies(query) }
    ).flow.cachedIn(viewModelScope)



}
