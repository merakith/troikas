package org.troikas.main.network

import android.util.Log
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository(
    private val openFoodFactsApi: FoodApi
) {
    // currently not using the openfoodfacts api
    suspend fun getProduct(barcode: String): ProductDetails? {
        return withContext(Dispatchers.IO) {
            try {
                openFoodFactsApi.getProductByBarcode(barcode).product
            } catch (e: Exception) {
                Log.e("FoodRepository", "Error fetching from OFF: ${e.message}")
                null
            }
        }
    }

    suspend fun queryProduct(barcode: String): Product? {
        return withContext(Dispatchers.IO) {
            try {
                SupabaseClient.client.from("products").select {
                    filter {
                        eq("barcode", barcode)
                    }
                }.decodeSingle<Product>()
            } catch (e: Exception) {
                Log.d("FoodRepository", "Supabase query failed: ${e.message}")
                null
            }
        }
    }
}
