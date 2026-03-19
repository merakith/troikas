package org.troikas.main.network

import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApi{
    @GET("api/v0/product/{barcode}.json")
    suspend fun getProductByBarcode(
        @Path("barcode") barcode: String
    ): FoodResponse
}