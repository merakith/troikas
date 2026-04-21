package org.troikas.main.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val barcode: String,
    val name: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    val allergens: List<String> = emptyList(),
    val additives: List<String> = emptyList(),
    @SerialName("ingredient_list") val ingredientList: String,
    val nutrients: Map<String, Double>? = null,
    // Aryan we need to fix the nutrients in our database to this format of "sugar": 12.00
    @SerialName("updated_at") val updatedAt: String? = null
)
