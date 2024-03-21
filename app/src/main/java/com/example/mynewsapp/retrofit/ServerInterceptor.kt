package com.example.mynewsapp.retrofit

import com.example.mynewsapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ServerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(requestInterceptor(chain.request()))
    }

    private fun requestInterceptor(request: Request): Request {
        return request.newBuilder()
            .header(Constants.ApiHeaderKey.API_KEY, Constants.ApiHeaderKey.API_KEY_VALUE)
            .build()
    }
}