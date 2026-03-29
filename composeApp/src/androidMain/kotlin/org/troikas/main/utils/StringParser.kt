package org.troikas.main.utils

class IngredientParser{
    fun parseIngredients(rawString: String): List<String>{
        return rawString
            .replace(Regex("\\(.*?\\)"), "")   
            .split(",","-", " and ", " And ", " AND ")
            .map{it.trim()}
            .filter{it.isNotEmpty()}
    }
}