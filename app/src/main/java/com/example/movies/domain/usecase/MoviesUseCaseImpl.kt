package com.example.movies.domain.usecase

import androidx.paging.PagingData
import com.example.movies.data.repo.MoviesDetailRepositoryImpl
import com.example.movies.data.repo.MoviesRepositoryImpl
import com.example.movies.domain.entity.MovieDetailModel
import com.example.movies.domain.entity.MovieModel
import com.example.movies.ui.util.NetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
    private val repo: MoviesRepositoryImpl,
    private val repoDetail: MoviesDetailRepositoryImpl,
    private val networkState: NetworkState = NetworkState
) : MovieUseCase {
    override suspend fun getMostPopularMovies(): Flow<PagingData<MovieModel>> {
        val movies = if (networkState.isOnline()) {
            repo.getPopularMovies().flow
        } else {
            repo.getCachedMovies().flow
        }
        return movies.distinctUntilChanged()
    }

    override suspend fun getMoviesDetail(id: Int): MovieDetailModel {
        return if (networkState.isOnline()) repoDetail.getMovieDetailsRemote(id)
        else repoDetail.getMovieDetailsLocal(id)
    }

    override suspend fun getSearchMovies(query: String): Flow<PagingData<MovieModel>> {
        return repo.getSearchMovies(query).flow.distinctUntilChanged()
    }

    override suspend fun getTopRatedMovies(): Flow<PagingData<MovieModel>> {
            return  repo.getTopRatedMovies().flow

    }


    override suspend fun getUpcomingMovies(): Flow<PagingData<MovieModel>> {
        return repo.getUpcomingMovies().flow

    }

}