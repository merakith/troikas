package org.troikas.main.utils

import org.troikas.main.database.IngredientClassification
import org.troikas.main.database.IngredientRepository
import org.troikas.main.network.FoodRepository
import org.troikas.main.network.Product
import org.troikas.main.network.SupabaseClient

data class ProductUseCase(val product: Product, val classification: List<IngredientClassification>)

class AnalyzeProductUseCase(
        private val foodrepo: FoodRepository,
        private val ingredientrepo: IngredientRepository,
        private val parser: StringParser
) {
    suspend fun execute(barcode: String): ProductUseCase? {
        val dbProduct = foodrepo.queryProduct(barcode) ?: return null

        val cleanList = parser.execute(dbProduct.ingredientList ?: "")
        println("DEBUG: Scanned Ingredients: $cleanList") // CHECK 

        var localResults = ingredientrepo.analyzeIngredients(cleanList)
        if (localResults.isEmpty()) {
            println("DEBUG: Local DB empty, fetching all from Supabase fallback...")
            localResults =
                    try {
                        // Fetch everything from the ingredients table

                        SupabaseClient.client
                                .from("ingredients")
                                .select {
                                    filter {
                                        or {
                                            isIn("name", cleanList)
                                            overlaps("synonyms", cleanList)
                                        }
                                    }
                                }
                                .decodeList<IngredientClassification>()
                    } catch (e: Exception) {
                        android.util.Log.e("AnalyzeUseCase", "Supabase Error: ${e.message}")
                        emptyList()
                    }
                    
        }

        println("DEBUG: Found ${localResults.size} matches to show.")
        return ProductUseCase(dbProduct, localResults)
    }
}
