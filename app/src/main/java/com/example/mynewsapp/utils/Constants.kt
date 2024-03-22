package com.example.mynewsapp.utils

object Constants {
    object ApiHeaderKey {
        const val API_KEY = "X-Api-Key"
        const val API_KEY_VALUE = "67501407e346464aad765c99a2f4fad2"
    }

    object ServerUrls {
        const val BASE_URL = "https://newsapi.org/"
        const val TOP_HEADINGS = "v2/top-headlines"
        const val EVERYTHING = "v2/everything"
    }

    object Error {
        const val NO_RESULTS = "No Results Found"
        const val SOMETHING_WENT_WRONG = "Something went wrong"
        const val NO_INTERNET = "No Internet"
    }
}