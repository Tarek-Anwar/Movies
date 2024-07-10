package com.example.movies.domain.usecase

import androidx.paging.PagingData
import com.example.movies.data.repo.MoviesRepositoryImpl
import com.example.movies.domain.entity.MovieModel
import com.example.movies.ui.util.NetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
    private val repo: MoviesRepositoryImpl,
    private val networkState: NetworkState = NetworkState
) : MovieUseCase {
    override suspend fun getMostPopularMovies(): Flow<PagingData<MovieModel>> {
        val movies = if (networkState.isOnline()) {
            repo.getPopularMovies().flow.distinctUntilChanged()
        } else {
            repo.getCachedMovies().flow.distinctUntilChanged()
        }
        return movies
    }


    override suspend fun getSearchMovies(query: String): Flow<PagingData<MovieModel>> {
        return repo.getSearchMovies(query).flow.distinctUntilChanged()
    }

    override suspend fun getTopRatedMovies(): Flow<PagingData<MovieModel>> {
        return repo.getTopRatedMovies().flow.distinctUntilChanged()
    }

    override suspend fun getUpcomingMovies(): Flow<PagingData<MovieModel>> {
        return repo.getUpcomingMovies().flow.distinctUntilChanged()
    }

}

