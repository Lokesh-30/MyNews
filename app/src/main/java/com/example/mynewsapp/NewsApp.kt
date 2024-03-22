package com.example.mynewsapp

import android.app.Application
import com.example.mynewsapp.retrofit.RestClient
import dagger.hilt.android.HiltAndroidApp

/**
 * Application Class
 */
@HiltAndroidApp
class NewsApp : Application() {
    companion object {
        lateinit var instance: NewsApp
        lateinit var retrofitBuilder: RestClient.Builder
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        retrofitBuilder = RestClient.Builder().initialize().build()
    }

    fun getRestClient(): RestClient.Builder {
        return retrofitBuilder
    }
}