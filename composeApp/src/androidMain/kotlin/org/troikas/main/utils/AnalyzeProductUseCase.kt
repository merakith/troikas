package org.troikas.main.utils

import org.troikas.main.network.FoodRepository
import org.troikas.main.database.IngredientRepository
import org.troikas.main.database.IngredientClassification

class AnalyzeProductUseCase(
    private val foodrepo: FoodRepository,
    private val ingredientrepo: IngredientRepository,
    private val parser:IngredientParser
){
    suspend fun execute(barcode:String): List<IngredientClassification>{
        val product=foodrepo.getProduct(barcode)?: return emptyList()
        val rawIngredients= product.ingredientsText
        if(rawIngredients.isNullOrEmpty())return emptyList()
        val cleanList= parser.parseIngredients(rawIngredients)
        val localResults=ingredientrepo.analyzeIngredients(cleanList)
        return localResults
    }
}