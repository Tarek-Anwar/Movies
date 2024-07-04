package com.example.movies.data.di

import com.example.movies.data.remote.ApiService
import com.example.movies.data.repo.MoviesRepoImpl
import com.example.movies.domain.repo.MoviesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

   /* @Provides
    fun provideRepo(apiService: ApiService): MoviesRepo {
        return MoviesRepoImpl(apiService)
    }*/
}