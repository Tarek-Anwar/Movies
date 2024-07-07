package com.example.movies.ui.di

import com.example.movies.data.local.MoviesDao
import com.example.movies.data.paging.LocalMoviesPagingSource
import com.example.movies.data.paging.RemoteMoviesPagingSource
import com.example.movies.data.remote.MoviesApiService
import com.example.movies.data.repo.MoviesRepositoryImpl
import com.example.movies.domain.repo.MoviesRepository
import com.example.movies.domain.usecase.MovieUseCase
import com.example.movies.domain.usecase.MoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class MoviesModule {

    @Binds
    abstract fun bindMoviesRepo(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository
    companion object {
        @Provides
        fun providesMoviesRepo(
            localPagingSource: LocalMoviesPagingSource,
            remotePagingSource: RemoteMoviesPagingSource
        ): MoviesRepositoryImpl {
            return MoviesRepositoryImpl(localPagingSource, remotePagingSource)
        }
    }

    @Binds
    abstract fun bindMoviesUseCase(moviesUseCaseImpl: MoviesUseCaseImpl): MovieUseCase
}

@Module
@InstallIn(ViewModelComponent::class)
class PagingModule {

    @ViewModelScoped
    @Provides
    fun providePagingModel(
        dao : MoviesDao,
        ): LocalMoviesPagingSource {
        return LocalMoviesPagingSource(dao)
    }
    @ViewModelScoped
    @Provides
    fun provideMoviesPagingRemote(
        dao : MoviesDao,
        api : MoviesApiService,
    ) : RemoteMoviesPagingSource{
        return RemoteMoviesPagingSource(moviesDao = dao , apiService = api)
    }

}