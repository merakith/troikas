package org.troikas.main.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeminiClient{
    private const val BASE_URL="https://generativelanguage.googleapis.com/"

    //this will only run when something asks for retrofit
    private val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: GeminiApi by lazy{
        retrofit.create(GeminiApi::class.java)
    }
}