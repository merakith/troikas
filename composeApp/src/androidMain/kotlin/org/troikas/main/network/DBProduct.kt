package org.troikas.main.database

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Product(
    val barcode: String,
    val name: String,
    @SerializedName("image_url") val imageUrl: String? = null,
    val allergens: List<String> = emptyList(),
    val additives: List<String> = emptyList(),
    @SerializedName("ingredient_list") val ingredientList: String,
    val nutrients: JsonObject? = null,
    @SerializedName("updated_at") val updatedAt: String? = null
)