package com.example.movies.data.di

import com.example.movies.domain.repo.MoviesRepo
import com.example.movies.domain.usecase.GetMoviesPopularUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

  /*  @Provides
    fun provideUseCase(mealRepo: MoviesRepo): GetMoviesPopularUserCase {
        return GetMoviesPopularUserCase(mealRepo)
    }*/
}