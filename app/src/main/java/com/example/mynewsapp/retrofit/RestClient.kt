package com.example.mynewsapp.retrofit

import com.example.mynewsapp.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {

    class Builder {
        private lateinit var httpClient: OkHttpClient.Builder
        private lateinit var restAdapter: Retrofit
        private lateinit var apiServices: ApiServices

        fun initialize(): Builder {
            httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(ServerInterceptor())
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
            return this
        }

        fun build(): Builder {
            restAdapter = Retrofit.Builder()
                .baseUrl(Constants.ServerUrls.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiServices = restAdapter.create(ApiServices::class.java)
            return this
        }

        fun getApiService(): ApiServices {
            return apiServices
        }
    }
}
