package com.example.movies.ui.fragment.detalis

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.MovieApp
import com.example.movies.data.local.MovieDataBase
import com.example.movies.domain.entity.MovieModelLocal
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel : ViewModel() {

    private var moviesDao = MovieDataBase.getDaoInstance(MovieApp.getApplication())

    private val _movieDetailState: MutableStateFlow<MovieModelLocal?> = MutableStateFlow(null)
    val movieDetailState: StateFlow<MovieModelLocal?> = _movieDetailState

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }


    fun getMovieDetail(id: Int) {
        viewModelScope.launch(errorHandler) {
            val movie = getMovieDetailFromLocal(id)
            _movieDetailState.value = movie
            Log.d(TAG, "vm getMovieDetail: test" + id)
        }
    }

    private suspend fun getMovieDetailFromLocal(id: Int) =
        withContext(Dispatchers.IO) { moviesDao.getMovieById(id)}

    private  val TAG = "MovieDetailsViewModel"
}