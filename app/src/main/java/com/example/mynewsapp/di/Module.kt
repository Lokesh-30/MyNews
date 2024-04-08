package com.example.mynewsapp.di

import com.example.mynewsapp.retrofit.ApiServices
import com.example.mynewsapp.retrofit.RestClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Class used as a injection module or dagger
 */
@InstallIn(SingletonComponent::class)
@Module
class Module {

    /**
     * News api service initializing
     */
    @Singleton
    @Provides
    fun provideApiServices(): ApiServices {
        return  RestClient.Builder().initialize().build().getApiService()
    }
}