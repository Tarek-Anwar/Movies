package com.example.movies.domain.usecase

import androidx.paging.PagingData
import com.example.movies.domain.entity.MovieModelRemote
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    suspend fun getCategoryMovies(category: String): Flow<PagingData<MovieModelRemote>>
}