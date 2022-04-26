package com.example.networking.network.retrofit

import com.example.networking.network.retrofit.services.PosterService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {
    const val IS_TESTER = true
    private const val SERVER_DEVELOPMENT = "https://jsonplaceholder.typicode.com/"
    private const val SERVER_PRODUCTION = "https://jsonplaceholder.typicode.com/"

    private val retrofit =
        Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create()).build()

    private fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val posterService: PosterService = retrofit.create(PosterService::class.java)
}

