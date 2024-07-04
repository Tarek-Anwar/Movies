package com.example.movies.data.di

import com.example.movies.data.remote.ApiService
import com.example.movies.data.repo.MoviesRepoImpl
import com.example.movies.domain.repo.MoviesRepo
import com.example.movies.domain.usecase.GetMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(mealRepo: MoviesRepo): GetMovies {
        return GetMovies(mealRepo)
    }
}