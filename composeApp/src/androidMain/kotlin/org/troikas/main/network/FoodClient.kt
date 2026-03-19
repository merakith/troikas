package org.troikas.main.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private const val BASE_URL="https://world.openfoodfacts.org/"

    //this will only run when something asks for retrofit
    private val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: OpenFoodApi by lazy{
        retrofit.create(OpenFoodApi::class.java)
    }
}