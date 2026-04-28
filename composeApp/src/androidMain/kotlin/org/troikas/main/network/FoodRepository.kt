package org.troikas.main.network

import android.util.Log
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository() {
    suspend fun queryProduct(barcode: String): Product? {
        Log.d("FoodRepository", "queryProduct called with barcode: $barcode")
        return withContext(Dispatchers.IO) {
            try {
                val response = SupabaseClient.client.from("products").select {
                    filter {
                        eq("barcode", barcode)
                    }
                }.decodeList<Product>()

                Log.d("FoodRepository", "Results found: ${response.size}")
                response.firstOrNull()
            } catch (e: Exception) {
                Log.d("FoodRepository", "Supabase query failed: ${e.message}")
                null
            }
        }
    }
}
