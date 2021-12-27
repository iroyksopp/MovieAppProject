package com.example.movieapp.model.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(
            OkHttpClient().newBuilder()
                .build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: Api by lazy { retrofit().create(Api::class.java) }
}