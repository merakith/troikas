package org.troikas.main

import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.runBlocking
import org.troikas.main.network.FoodRepository
import org.troikas.main.network.SupabaseClient
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DatabaseIntegrationTest {

    // 1. Test the Food API (OpenFoodFacts)
    @Test
    fun testDietCokeLookup() = runBlocking {
        val repo = FoodRepository()
        // Barcode for Diet Coke
        val barcode = "8901764061257" 
        
        // Use the function name you have in your FoodRepository (queryProduct)
        val product = repo.queryProduct(barcode)
        
        assertNotNull(product, "Error: Diet Coke not found in OpenFoodFacts!")
        assertTrue(
            product.name?.contains("Coke", ignoreCase = true) == true, 
            "Error: Product found but name doesn't match Coke"
        )
    }

    // 2. Test the Supabase Connection
    @Test
    fun testSupabaseConnection() = runBlocking {
        try {
            // Try to fetch just 1 row from your products table
            val response = SupabaseClient.client.postgrest["products"]
                .select {
                    limit(1)
                }
            
            assertNotNull(response, "Error: Could not connect to Supabase products table!")
            println("Supabase connection successful.")
        } catch (e: Exception) {
            println("Supabase Test Failed: ${e.message}")
            throw e
        }
    }
}