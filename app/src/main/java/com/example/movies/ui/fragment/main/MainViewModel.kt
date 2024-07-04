package com.example.movies.ui.fragment.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.remote.ApiService
import com.example.movies.domain.entity.MoviesResponse
import com.example.movies.listOfMovies
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainViewModel : ViewModel() {

    private var apiService: ApiService

    private val _moviesState: MutableStateFlow<MoviesResponse?> = MutableStateFlow(null)
    val moviesState: StateFlow<MoviesResponse?> = _moviesState

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        getMovies2()
    }

    fun getMovies() = listOfMovies
    fun getMovies2() {
        viewModelScope.launch(errorHandler) {
            val movies = getPopularMovies()
            _moviesState.value = movies
        }
    }

    private suspend fun getPopularMovies() =
        withContext(Dispatchers.IO) { apiService.getPopularMovies() }

    private val TAG = "MainViewModel"

}