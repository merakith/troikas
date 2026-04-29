package org.troikas.main.network

import com.google.gson.annotations.SerializedName 

data class FoodResponse(
    val code: String,
    val product: ProductDetails?
)

data class ProductDetails(
    @SerializedName("product_name") val productName: String?,
    @SerializedName("ingredients_text") val ingredientsText: String?,
    val nutrients: Nutrients?
)

data class Nutrients(
    @SerializedName("sugars_100g") val sugarGrams: Double?,
    @SerializedName("proteins_100g") val proteinsGrams: Double?   
)