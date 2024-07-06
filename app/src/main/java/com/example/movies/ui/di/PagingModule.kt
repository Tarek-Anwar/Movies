package com.example.movies.ui.di

import com.example.movies.data.local.MoviesDao
import com.example.movies.data.paging.LocalMoviesPagingSource
import com.example.movies.data.paging.RemoteMoviesPagingSource
import com.example.movies.data.remote.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PagingModule {

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
        return RemoteMoviesPagingSource(moviesDao = dao , moviesApiService = api)
    }
}