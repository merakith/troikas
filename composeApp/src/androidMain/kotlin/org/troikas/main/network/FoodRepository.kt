package org.troikas.main.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository(private val openFoodFactsApi: FoodApi
// supabase client will be injected here
) {
    suspend fun getProduct(barcode: String): ProductDetails? {
        // forces execution to background thread pool
        return withContext(Dispatchers.IO) {
            val offResponse =
                    try {
                        openFoodFactsApi.getProductByBarcode(barcode).product
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }

            val offIngredients = offResponse?.ingredientsText

            if (!offIngredients.isNullOrBlank()) {
                println("Found in openFoodFacts")
                offResponse
            } else {
                println("check supabase")
                getProductFromSupabase(barcode)
            }
        }
    }
    private suspend fun getProductFromSupabase(barcode: String): ProductDetails? {
        return try {
            // todo: supabase sdk call will go here
            null
        } catch (e: Exception) {
            println("supabase connection failed: ${e.message}")
            null
        }
    }
}