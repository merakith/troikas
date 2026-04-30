package org.troikas.main.utils

import io.github.jan.supabase.postgrest.from
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
        val results = ingredientrepo.getFuzzyClassification(cleanList) // repo handles fallback now
        return ProductUseCase(dbProduct, results)
    }
}
