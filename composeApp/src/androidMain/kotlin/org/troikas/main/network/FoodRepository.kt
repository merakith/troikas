package org.troikas.main.network

import android.util.Log
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository(
    private val openFoodFactsApi: FoodApi
// supabase client will be injected here
) {
    suspend fun getProduct(barcode: String): ProductDetails? {
        // forces execution to background thread pool
        return withContext(Dispatchers.IO) {
            val offResponse = try {
                openFoodFactsApi.getProductByBarcode(barcode).product
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            val offIngredients = offResponse?.ingredientsText

            (if (!offIngredients.isNullOrBlank()) {
                Log.d("FoodRepository", "found at OpenFoodFacts")
                offResponse
            } else {
                Log.d("FoodRepository", "not found at OpenFoodFacts")
                Log.d("FoodRepository", "checking database")
                queryProduct(barcode)
            }) as ProductDetails?
        }
    }

    suspend fun queryProduct(barcode: String): Product? {
        return try {
            SupabaseClient.client.from("products").select {
                filter {
                    val filter: PostgrestFilterBuilder = this
                    filter.eq("barcode", barcode)
                }
            }.decodeSingle<Product>()
        } catch (e: Exception) {
            Log.d("FoodRepository", "supabase connection failed: ${e.message}")
            null
        }
    }
}
