package org.troikas.main.utils

import org.troikas.main.database.IngredientClassification
import org.troikas.main.database.IngredientRepository
import org.troikas.main.network.FoodRepository
import org.troikas.main.network.Product


data class ProductUseCase(
    val product: Product,
    val classification: List<IngredientClassification>
)

class AnalyzeProductUseCase(
        private val foodrepo: FoodRepository,
        private val ingredientrepo: IngredientRepository,
        private val parser: stringParser
) {
    suspend fun execute(barcode: String): ProductUseCase? {
        val dbProduct = foodrepo.queryProduct(barcode)
        if (dbProduct != null) {
            val cleanList = parser.stringParser(dbProduct.ingredientList)
            val localResults = ingredientrepo.analyzeIngredients(cleanList)
            return ProductUseCase(dbProduct, localResults)
        }
        return null
    }
}
