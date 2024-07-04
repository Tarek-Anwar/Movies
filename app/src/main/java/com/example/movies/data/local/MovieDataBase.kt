package com.example.movies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.domain.entity.MovieModelLocal

@Database(
    entities = [MovieModelLocal::class],
    version = 1,
    exportSchema = false,
)
abstract class MovieDataBase : RoomDatabase() {

    abstract val dao: MoviesDao

    companion object {
        @Volatile
        private var daoInstance: MoviesDao? = null
        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDataBase::class.java,
                "movies.db"
            ).fallbackToDestructiveMigration().build()

        fun getDaoInstance(context: Context): MoviesDao {
            synchronized(this) {
                if (daoInstance == null) {
                    daoInstance = buildDataBase(context).dao
                }
                return daoInstance as MoviesDao
            }
        }
    }

}
