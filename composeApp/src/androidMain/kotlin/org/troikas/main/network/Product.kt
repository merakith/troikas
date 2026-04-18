package org.troikas.main.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.JsonObject

@Serializable
data class Product(
    val barcode: String,
    val name: String?=null,
    @SerialName("image_url") val imageUrl: String? = null,
    val allergens: List<String> = emptyList(),
    val additives: List<String> = emptyList(),
    @SerialName("ingredient_list") val ingredientList: String,
    val nutrients: JsonObject? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)