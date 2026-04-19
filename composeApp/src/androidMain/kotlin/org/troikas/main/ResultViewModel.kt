package org.troikas.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.troikas.main.database.Category
import org.troikas.main.database.IngredientClassification
import org.troikas.main.database.IngredientDao
import org.troikas.main.network.FoodRepository

class ResultViewModel : ViewModel() {

    var productName by mutableStateOf<String?>(null)
        private set

    var ingredientsText by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var avoidIngredients by mutableStateOf<List<IngredientClassification>>(emptyList())
        private set

    var moderateIngredients by mutableStateOf<List<IngredientClassification>>(emptyList())
        private set

    var goodIngredients by mutableStateOf<List<IngredientClassification>>(emptyList())
        private set

    private val foodRepository = FoodRepository()

    fun loadProduct(barcode: String, dao: IngredientDao) {
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                // Step 1 — fetch product from OpenFoodFacts API
                val product = foodRepository.getProduct(barcode)
                productName = product?.productName ?: barcode
                ingredientsText = product?.ingredientsText

                // Step 2 — parse raw ingredient text into list of names
                val names = parseIngredients(product?.ingredientsText ?: "")

                // Step 3 — look up names in local Room DB
                val found = withContext(Dispatchers.IO) {
                    dao.getClassifications(names)
                }

                // Step 4 — split results by category
                avoidIngredients = found.filter { it.category == Category.AVOID }
                moderateIngredients = found.filter { it.category == Category.MODERATE }
                goodIngredients = found.filter { it.category == Category.HEALTHY }

            } catch (e: Exception) {
                error = "Could not load product: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // "water, sugar, salt (iodized)" → ["water", "sugar", "salt iodized"]
    private fun parseIngredients(text: String): List<String> {
        return text
            .split(",", ";")
            .map { it.trim().lowercase().replace(Regex("[^a-z0-9 ]"), "") }
            .filter { it.isNotEmpty() }
    }
}