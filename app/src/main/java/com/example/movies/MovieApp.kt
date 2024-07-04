package com.example.movies

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp : Application() {
    companion object {
        private lateinit var application: MovieApp
        fun getApplication(): Context {
            return application.applicationContext
        }
    }
    init {
        application = this
    }
}