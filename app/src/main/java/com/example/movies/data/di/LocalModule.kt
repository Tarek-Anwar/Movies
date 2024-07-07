package com.example.movies.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.data.local.MoviesDao
import com.example.movies.domain.entity.MovieModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): MoviesDao {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDataBase::class.java,
            "movies.db"
        ).fallbackToDestructiveMigration().build().dao()
    }

}

@Database(entities = [MovieModel::class], version = 1, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun dao(): MoviesDao

}
